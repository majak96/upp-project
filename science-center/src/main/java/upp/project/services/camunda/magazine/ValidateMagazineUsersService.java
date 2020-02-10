package upp.project.services.camunda.magazine;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;
import upp.project.services.MagazineService;

@Service
public class ValidateMagazineUsersService implements JavaDelegate{
	
	@Autowired
	MagazineService magazineService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("MAG | validation of magazine reviewers and editors data");
		
		boolean validation = true;
		
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("formData");
		
		for(FormValueDTO formValue : formValues) {
			//check specific fields
			if(formValue.getId().equals("form_reviewers")) {	
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
