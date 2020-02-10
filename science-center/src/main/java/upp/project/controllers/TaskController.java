package upp.project.controllers;

import java.security.Principal;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
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
import upp.project.dtos.SubmitResponseDTO;
import upp.project.dtos.TaskDTO;
import upp.project.services.ProcessService;
import upp.project.services.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {
	
	@Autowired
	ProcessService processService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<?> getUserTasks() {
		
		System.out.println("Getting a list of tasks.");
		
		if(identityService.getCurrentAuthentication() == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
				
		String currentUser = identityService.getCurrentAuthentication().getUserId();
		
		//get all tasks that belong to the signed in user
		List<TaskDTO> tasks = processService.getTasksForUser(currentUser);
		
		return ResponseEntity.ok(tasks);
	}
	
	@GetMapping("/task/{taskId}")
	public ResponseEntity<?> getTask(Principal principal, @PathVariable String taskId) {
		
		System.out.println("Getting a task.");
				
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		//check if task exists
		if(task == null) {
			return new ResponseEntity<>("The task doesn't exist!", HttpStatus.NOT_FOUND);
		}
		
		//get fields for the user task
		List<FormFieldDTO> fieldList = processService.getFrontendFields(taskId);
				
		FormDTO form = new FormDTO(task.getId(), task.getProcessInstanceId(), task.getName(), fieldList);
		
		return ResponseEntity.ok(form);
	}
	
	@PostMapping("/{taskId}")
	public ResponseEntity<?> submitForm(@RequestBody List<FormValueDTO> formValues, @PathVariable String taskId) {
		
		System.out.println("Submiting the form values.");
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();	
		
		//check if task exists
		if(task == null) {
			return new ResponseEntity<>("The task doesn't exist!", HttpStatus.NOT_FOUND);
		}
				
		String processInstanceId = task.getProcessInstanceId();
		
		//save form values as a process variable
		processService.setProcessVariable(processInstanceId, "formData", formValues);
		
		for(FormValueDTO formValue : formValues) {
			if(formValue.getValue() instanceof List) {	
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
		
		if(!processService.processInstanceExists(processInstanceId)) {
			return ResponseEntity.ok(new SubmitResponseDTO());
		}
		
		Object redirectLink = processService.getProcessVariable(processInstanceId, "redirect_link");
		System.out.println("LINK HERE:" + redirectLink);
		if(redirectLink != null) {
			processService.setProcessVariable(processInstanceId, "redirect_link", null);
		}
		
		String currentUser = identityService.getCurrentAuthentication() == null ? "guest" : identityService.getCurrentAuthentication().getUserId();
		
		//find the next task
		Task nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee(currentUser).singleResult();		
		Boolean valid = (Boolean) processService.getProcessVariable(processInstanceId, "data_check");
				
		//return task id of the next task if it exists
		if(nextTask != null && valid != null) {
			return ResponseEntity.ok(new SubmitResponseDTO(nextTask.getId(), valid, redirectLink));
		}
		else if(redirectLink != null) {
			return ResponseEntity.ok(new SubmitResponseDTO(redirectLink));
		}		
		else{
			return ResponseEntity.ok(new SubmitResponseDTO());
		}
	}
	
	private boolean checkTaskGroup(Task task, String groupName) {
		
		List<Task> tasks = processService.getGroupTasks(groupName);
		
		for(Task t : tasks) {
			if(t.getId().equals(task.getId())) {
				return true;
			}
		}
		
		return false;
	}

}
