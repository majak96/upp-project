package upp.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upp.project.dtos.FormDTO;
import upp.project.dtos.FormFieldDTO;
import upp.project.dtos.PaperDTO;
import upp.project.model.Issue;
import upp.project.model.Paper;
import upp.project.services.IssueService;
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
	IssueService issueService;
	
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
	
	@GetMapping("issue/{issueId}")
	public ResponseEntity<?> getIssuePapers(@PathVariable Long issueId) {
		
		//check if issue exists
		Issue issue = issueService.findById(issueId);
		if(issue == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		List<Paper> paperList = paperService.findByIssue(issue);
		
		List<PaperDTO> dtos = new ArrayList<PaperDTO>();
		for(Paper paper : paperList) {
			dtos.add(new PaperDTO(paper));
		}
				
		return ResponseEntity.ok(dtos);
	}

}
