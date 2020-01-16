package upp.project.controllers;

import java.util.List;

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
import upp.project.dtos.TaskDTO;
import upp.project.services.ProcessService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	@Autowired
	ProcessService processService;
	
	@Autowired
	TaskService taskService;
	
	@GetMapping("/task")
	public ResponseEntity<?> getUserTasks() {
		
		System.out.println("Getting a list of tasks.");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//get all tasks that belong to the signed in users
		List<TaskDTO> tasks = processService.getTasksForUser(username);
		
		return ResponseEntity.ok(tasks);
	}
	
	@GetMapping("/task/{taskId}")
	public ResponseEntity<?> getTask(@PathVariable String taskId) {
		
		System.out.println("Getting a task.");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		//check if task exists
		if(task == null) {
			return new ResponseEntity<>("The task doesn't exist!", HttpStatus.NOT_FOUND);
		}
		
		//check if task belongs to the signed in user
		if(!username.equals(task.getAssignee())) {
			return new ResponseEntity<>("You are not authorized to see this task!", HttpStatus.UNAUTHORIZED);
		}
		
		//get fields for the user task
		List<FormFieldDTO> fieldList = processService.getFrontendFields(taskId);
		
		FormDTO form = new FormDTO(task.getId(), task.getProcessInstanceId(), task.getName(), fieldList);
		
		return ResponseEntity.ok(form);
	}
	
	@PostMapping("/task/{taskId}")
	public ResponseEntity<?> submitTask(@RequestBody List<FormValueDTO> formValues, @PathVariable String taskId){
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		//check if task exists
		if(task == null) {
			return new ResponseEntity<>("The task doesn't exist!", HttpStatus.NOT_FOUND);
		}
		
		//check if task belongs to the signed in user
		if(!username.equals(task.getAssignee())) {
			return new ResponseEntity<>("You are not authorized to submit this task!", HttpStatus.UNAUTHORIZED);
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

}
