package upp.project.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upp.project.dtos.FormDTO;
import upp.project.dtos.FormFieldDTO;
import upp.project.dtos.FormValueDTO;
import upp.project.services.ProcessService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/magazine", produces = MediaType.APPLICATION_JSON_VALUE)
public class MagazineController {

	@Autowired
	ProcessService processService;
	
	@Autowired
	TaskService taskService;
	
	@GetMapping("/")
	public ResponseEntity<?> startNewMagazineProcess() {
		
		System.out.println("Starting the process of creating a new magazine.");
		
		//start the process and get the first task
		Task firstTask = processService.startProcess("adding_new_magazine"); 
		
		//get fields for the user task
		List<FormFieldDTO> frontendFields = processService.getFrontendFields(firstTask.getId());
		
		for(FormFieldDTO field : frontendFields) {
			if(field.getId().equals("form_scientific_area")) {
				field.setMultiple(true);
			}
		}
		
		FormDTO form = new FormDTO(firstTask.getId(), firstTask.getProcessInstanceId(), firstTask.getName(), frontendFields);
		
		return ResponseEntity.ok(form);
	}
	
	@PostMapping("/{taskId}")
	public ResponseEntity<?> create(@RequestBody List<FormValueDTO> formValues, @PathVariable String taskId) {
		
		System.out.println("Submiting the form field values for a new magazine.");
		
		String processId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
		
		//save form values as a process variable
		processService.setProcessVariable(processId, "newMagazineFormValues", formValues);
		
		//field validation and additional things
		for(FormValueDTO formValue : formValues) {
			if(formValue.getId().equals("form_issn")) {
				if(!validateISSN(formValue.getValue())) {
					return new ResponseEntity<>("ISSN must be an eight-digit number!", HttpStatus.BAD_REQUEST);
				}
			}
			else if(formValue.getId().equals("form_scientific_area")) {	
				if(!validateScientificAreas(formValue.getValue())) {
					return new ResponseEntity<>("You must choose at least one scientific area.", HttpStatus.BAD_REQUEST);
				}
				formValue.setValue(null);
			}
		}
		
		//submit form fields
		try {
			processService.submitFormFields(taskId, formValues);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
				
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/{processInstanceId}")
	public ResponseEntity<?> getUsersForm(@PathVariable String processInstanceId) {
		
		Task nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey("Task_0l9bab8").singleResult();
		
		//get fields for the user task
		List<FormFieldDTO> frontendFields = processService.getFrontendFields(nextTask.getId());
		
		for(FormFieldDTO field : frontendFields) {
				field.setMultiple(true);
		}
		
		FormDTO form = new FormDTO(nextTask.getId(), processInstanceId, nextTask.getName(), frontendFields);
		
		return ResponseEntity.ok(form);
	}
	
	@PostMapping("/{magazineId}/{taskId}")
	public ResponseEntity<?> addUsers(@RequestBody List<FormValueDTO> formValues, @PathVariable String taskId) {
		
		System.out.println("Submiting the form field values for editors and reviewers.");
		
		String processId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
		processService.setProcessVariable(processId, "newMagazineUsersFormValues", formValues);
		
		for(FormValueDTO formValue : formValues) {
			formValue.setValue(null);
		}
		
		//submit form fields
		try {
			processService.submitFormFields(taskId, formValues);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
				
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private boolean validateScientificAreas(String scientificAreas) {		
		String[] scientificAreasArray = scientificAreas.split(",");
		
		if(scientificAreasArray.length >= 1) {
			return true;
		}
		
		return false;
	}
	
	private boolean validateISSN(String ISSN) {		
		String regex = "[0-9]+";
		
		Pattern pattern = Pattern.compile(regex);	
		Matcher matcher = pattern.matcher(ISSN);

		
		if(ISSN.length() == 8 && matcher.matches()) {
			return true;
		}
		
		return false;
	}
}
