package upp.project.services.camunda.paper;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;
import upp.project.model.Issue;
import upp.project.model.Magazine;
import upp.project.model.Paper;
import upp.project.model.RegisteredUser;
import upp.project.model.Role;
import upp.project.model.ScientificArea;
import upp.project.services.IssueService;
import upp.project.services.MagazineService;
import upp.project.services.PaperService;
import upp.project.services.ScientificAreaService;
import upp.project.services.UserService;

@Service
public class SavePaperService implements JavaDelegate {
	
	@Autowired
	PaperService paperService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	IssueService issueService;
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	ScientificAreaService scientificAreaService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | saving the paper");
		
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("formData");
		
		String chosenMagazine = (String) execution.getVariable("form_magazine");		
		Long magazineId = Long.parseLong(chosenMagazine);
		
		Magazine magazine = magazineService.findById(magazineId);
		
		//create and save a new paper
		Paper newPaper = createPaper(formValues, magazine);			
		paperService.save(newPaper);		
		
		//save paper id as a process variable
		execution.setVariable("paperId", newPaper.getId());
		
		//save editor in chief username as a process variable
		execution.setVariable("magazine_editor_in_chief", newPaper.getIssue().getMagazine().getEditorInChief().getUsername());

	}
	
	Paper createPaper(List <FormValueDTO> formValues, Magazine magazine) {
		
		HashMap<String, Object> valuesMap = new HashMap<String,Object>();
		
		for(FormValueDTO value : formValues) {
			valuesMap.put(value.getId(), value.getValue());
		}
		
		Paper paper = new Paper((String) valuesMap.get("form_title"), (String) valuesMap.get("form_keywords"), (String) valuesMap.get("form_abstract"));
		
		//set chosen scientific area
		Long scientificAreaId = Long.parseLong((String)valuesMap.get("form_scientific_area"));
		ScientificArea scientificArea = scientificAreaService.findById(scientificAreaId);		
		if(scientificArea != null) {
			paper.setScientificArea(scientificArea);
		}
		
		//set logged in user as the author
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		RegisteredUser loggedInUser = userService.findByUsername(username);		
		if(loggedInUser != null && loggedInUser.getAuthority().getRole() == Role.ROLE_AUTHOR) {
			paper.setAuthor(loggedInUser);
		}
		
		//TODO: change to real PDF file
		paper.setPdf((String) valuesMap.get("form_pdf"));
		
		//set issue in which it will be published
		Issue issue = issueService.findUnpublishedIssueInMagazine(magazine);
		paper.setIssue(issue);
				
		return paper;		
	}

}
