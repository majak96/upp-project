package upp.project.services.camunda.paper;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;
import upp.project.model.CoAuthor;
import upp.project.model.Paper;
import upp.project.services.CoAuthorService;
import upp.project.services.MagazineService;
import upp.project.services.PaperService;

@Service
public class SavePaperCoAuthorsService implements JavaDelegate {
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	PaperService paperService;
	
	@Autowired
	CoAuthorService coAuthorService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | saving paper co-authors");
		
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("formData");
		
		HashMap<String, Object> valuesMap = new HashMap<String,Object>();
		
		for(FormValueDTO value : formValues) {
			valuesMap.put(value.getId(), value.getValue());
		}
		
		Long paperId = (Long) execution.getVariable("paperId");
		Paper paper = paperService.findById(paperId);
		
		if(paper != null) {
			//create and save the new co-author
			CoAuthor coauthor = new CoAuthor((String) valuesMap.get("form_coauthor_first_name"), (String) valuesMap.get("form_coauthor_last_name"),
											(String) valuesMap.get("form_coauthor_email"), (String) valuesMap.get("form_coauthor_city"), (String) valuesMap.get("form_coauthor_country"));			
			coAuthorService.save(coauthor);
			
			//add the new co-author to the paper
			paper.getCoauthors().add(coauthor);
			paperService.save(paper);
		}
		
	}
}
