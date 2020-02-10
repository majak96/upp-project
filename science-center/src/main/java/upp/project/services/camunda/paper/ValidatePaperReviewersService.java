package upp.project.services.camunda.paper;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;

@Service
public class ValidatePaperReviewersService implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | validation of paper reviewers data");
		
		boolean validation = true;
		
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("formData");
		
		for(FormValueDTO formValue : formValues) {
			//check specific fields
			if(formValue.getId().equals("form_chosen_reviewers")) {	
				if(!validateReviewers((List<String>)formValue.getValue())) {
					validation = false;
					break;
				}
			}
		}
		
		if(!validation) {
			execution.setVariable("data_check", false);
		}
		else {
			execution.setVariable("data_check", true);
		}
		
	}
		
	private boolean validateReviewers(List<String> reviewers) {				
		if(reviewers.size() >= 2) {
			return true;
		}
		
		return false;
	}

}
