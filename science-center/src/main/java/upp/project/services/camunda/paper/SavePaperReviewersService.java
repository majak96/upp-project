package upp.project.services.camunda.paper;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;
import upp.project.model.Paper;
import upp.project.services.PaperService;
import upp.project.services.UserService;

@Service
public class SavePaperReviewersService implements JavaDelegate {
		
	@Autowired
	PaperService paperService;
	
	@Autowired
	UserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | save reviewers for the paper");
						
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("formData");
		
		HashMap<String, Object> valuesMap = new HashMap<String,Object>();
		
		for(FormValueDTO value : formValues) {
			valuesMap.put(value.getId(), value.getValue());
		}
		
		Long paperId = (Long) execution.getVariable("paperId");			
		Paper paper = paperService.findById(paperId);
		
		if(paper != null) {
			List<String> reviewers = ((List<String>) valuesMap.get("form_chosen_reviewers"));
			
			//save the list of chosen reviewers as a process variable
			execution.setVariable("chosen_reviewers_list", reviewers);				
		}		
	}	
	
}
