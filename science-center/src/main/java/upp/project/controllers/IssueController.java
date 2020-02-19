package upp.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upp.project.dtos.IssueDTO;
import upp.project.model.Issue;
import upp.project.model.Magazine;
import upp.project.services.IssueService;
import upp.project.services.MagazineService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/issue", produces = MediaType.APPLICATION_JSON_VALUE)
public class IssueController {

	@Autowired
	IssueService issueService;
	
	@Autowired
	MagazineService magazineService;
	
	@GetMapping("magazine/{magazineId}")
	public ResponseEntity<?> getMagazineIssues(@PathVariable Long magazineId) {
		
		//check if magazine exists
		Magazine magazine = magazineService.findById(magazineId);
		if(magazine == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		List<Issue> issueList = issueService.findByMagazine(magazine);
		
		List<IssueDTO> dtos = new ArrayList<IssueDTO>();
		for(Issue issue : issueList) {
			dtos.add(new IssueDTO(issue));
		}
				
		return ResponseEntity.ok(dtos);
	}

}
