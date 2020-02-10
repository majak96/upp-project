package upp.project.controllers;

import java.util.List;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upp.project.dtos.FormDTO;
import upp.project.dtos.FormFieldDTO;
import upp.project.services.PaperService;
import upp.project.services.ProcessService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/paper", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaperController {
	
	@Autowired
	ProcessService processService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	PaperService paperService;
	
	@GetMapping("/form")
	public ResponseEntity<?> startNewPaperProcess() {
		
		System.out.println("PAP | starting the process of submiting a new paper.");
				
		//start the process and get the first task
		Task firstTask = processService.startProcess("processing_submitted_text"); 
				
		//get fields for the user task
		List<FormFieldDTO> frontendFields = processService.getFrontendFields(firstTask.getId());
		
		FormDTO form = new FormDTO(firstTask.getId(), firstTask.getProcessInstanceId(), firstTask.getName(), frontendFields);
		
		return ResponseEntity.ok(form);
	}

}
