package upp.project.services.camunda.paper;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;

@Service
public class ValidatePaperCoAuthorsService implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | validation of paper co-authors");
		
		boolean validation = true; 
		
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("formData");
			
		for(FormValueDTO value : formValues) {
			// check for required fields
			if(value.getValue() == null) {
				validation = false;
				break;
			}
			//check specific fields
			else if(value.getId().equals("form_coauthor_email")) {
				if(!validateEmailAddress((String) value.getValue())){
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
	
	private boolean validateEmailAddress(String email) {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		 
		Pattern pattern = Pattern.compile(regex);	
		Matcher matcher = pattern.matcher(email);
						
		if(matcher.matches()) {
			return true;
		}
		
		return false;
	}
}
