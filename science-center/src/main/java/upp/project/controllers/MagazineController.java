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
import org.springframework.security.core.context.SecurityContextHolder;
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
import upp.project.dtos.SubmitResponseDTO;
import upp.project.model.Magazine;
import upp.project.services.MagazineService;
import upp.project.services.ProcessService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/magazine", produces = MediaType.APPLICATION_JSON_VALUE)
public class MagazineController {

	@Autowired
	ProcessService processService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	MagazineService magazineService;
	
	@GetMapping("")
	public ResponseEntity<?> getActiveMagazines() {
		
		List<Magazine> activeMagazines = magazineService.getActiveMagazines();
		
		return ResponseEntity.ok(activeMagazines);
	}
	
	@GetMapping("/form")
	public ResponseEntity<?> startNewMagazineProcess() {
		
		System.out.println("Starting the process of creating a new magazine.");
		
		//get username of the logged in user
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//start the process and get the first task
		Task firstTask = processService.startProcess("adding_new_magazine"); 
		
		processService.setProcessVariable(firstTask.getProcessInstanceId(), "process_initiator", username);
		
		//get fields for the user task
		List<FormFieldDTO> frontendFields = processService.getFrontendFields(firstTask.getId());
		
		FormDTO form = new FormDTO(firstTask.getId(), firstTask.getProcessInstanceId(), firstTask.getName(), frontendFields);
		
		return ResponseEntity.ok(form);
	}
	
	@PostMapping("/form/{taskId}")
	public ResponseEntity<?> submitNewMagazineForm(@RequestBody List<FormValueDTO> formValues, @PathVariable String taskId) {
		
		System.out.println("Submiting the form field values for a new magazine.");
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();	
		String processInstanceId = task.getProcessInstanceId();
		
		//save form values as a process variable
		processService.setProcessVariable(processInstanceId, "newMagazineFormValues", formValues);
		
		//field validation and additional things
		for(FormValueDTO formValue : formValues) {
			if(formValue.getId().equals("form_issn")) {
				if(!validateISSN(formValue.getValue())) {
					return new ResponseEntity<>("ISSN must be a unique eight-digit number!", HttpStatus.BAD_REQUEST);
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
		
		//return the next task
		Task nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
				
		return ResponseEntity.ok(new SubmitResponseDTO(nextTask.getId()));
	}
	
	@PostMapping("/form/users/{taskId}")
	public ResponseEntity<?> addUsers(@RequestBody List<FormValueDTO> formValues, @PathVariable String taskId) {
		
		System.out.println("Submiting the form field values for magazine editors and reviewers.");
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();	
		String processInstanceId = task.getProcessInstanceId();
		
		//save form values as a process variable
		processService.setProcessVariable(processInstanceId, "newMagazineUsersFormValues", formValues);
		
		//field validation and additional things
		for(FormValueDTO formValue : formValues) {
			if(formValue.getId().equals("form_reviewers")) {	
				if(!validateReviewers(formValue.getValue())) {
					return new ResponseEntity<>("You must choose at least two reviewers.", HttpStatus.BAD_REQUEST);
				}
				formValue.setValue(null);
			}
			else if(formValue.getId().equals("form_editors")) {	
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
	
	private boolean validateScientificAreas(String scientificAreas) {		
		String[] scientificAreasArray = scientificAreas.split(",");
		
		if(scientificAreasArray.length >= 1) {
			return true;
		}
		
		return false;
	}
	
	private boolean validateReviewers(String reviewers) {		
		String[] reviewersArray = reviewers.split(",");
		
		if(reviewersArray.length >= 1) {
			return true;
		}
		
		return false;
	}
	
	private boolean validateISSN(String ISSN) {		
		String regex = "[0-9]+";
		
		Pattern pattern = Pattern.compile(regex);	
		Matcher matcher = pattern.matcher(ISSN);

		Magazine magazine = magazineService.findByISSN(ISSN);
		
		if(magazine == null && ISSN.length() == 8 && matcher.matches()) {
			return true;
		}
		
		return false;
	}
}
