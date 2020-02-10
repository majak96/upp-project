package upp.project.controllers;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
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
	IdentityService identityService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	MagazineService magazineService;
	
	
	@GetMapping("/form")
	public ResponseEntity<?> startNewMagazineProcess() {
		
		System.out.println("MAG | starting the process of creating a new magazine.");
				
		//start the process and get the first task
		Task firstTask = processService.startProcess("adding_new_magazine"); 
				
		//get fields for the user task
		List<FormFieldDTO> frontendFields = processService.getFrontendFields(firstTask.getId());
		
		FormDTO form = new FormDTO(firstTask.getId(), firstTask.getProcessInstanceId(), firstTask.getName(), frontendFields);
		
		return ResponseEntity.ok(form);
	}
	
	@GetMapping("")
	public ResponseEntity<?> getActiveMagazines() {
		
		List<Magazine> activeMagazines = magazineService.getActiveMagazines();
		
		return ResponseEntity.ok(activeMagazines);
	}
	
	/*@GetMapping("/issue/{magazineId}")
	public ResponseEntity<?> findMagazinePapers(@PathVariable Long magazineId) {
		
		System.out.println("Getting magazine issues.");
		
		//check if magazine exists
		Magazine magazine = magazineService.findById(magazineId);
		if(magazine == null || !magazine.isActive()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		Set<Issue> issues = magazine.getIssues();
		Set<IssueDTO> dtos = new HashSet<IssueDTO>();
		
		for(Issue issue : issues) {
			dtos.add(new IssueDTO(issue));;
		}
		
		return ResponseEntity.ok(dtos);
	}
	*/
	
	/*
	 * @PostMapping("/issue/{magazineId}") public ResponseEntity<?>
	 * addIssueToMagazine(@PathVariable Long magazineId, @RequestBody @Valid
	 * IssueDTO issue) {
	 * 
	 * //check if magazine exists Magazine magazine =
	 * magazineService.findById(magazineId); if(magazine == null ||
	 * !magazine.isActive()) { return
	 * ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); }
	 * 
	 * Issue issue = i
	 * 
	 * }
	 */
	
}
