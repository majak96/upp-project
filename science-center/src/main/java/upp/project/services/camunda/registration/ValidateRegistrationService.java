package upp.project.services.camunda.registration;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;
import upp.project.model.RegisteredUser;
import upp.project.services.UserService;

@Service
public class ValidateRegistrationService implements JavaDelegate{
	
	@Autowired
	UserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("REG | validation of registration data");
		
		boolean validation = true; 
		
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("formData");
				
		for(FormValueDTO formValue : formValues) {
			// check for required fields
			if(!formValue.getId().equals("form_title") && formValue.getValue() == null) {
				validation = false;
				break;
			}
			//check specific fields
			else if (formValue.getId().equals("form_email")) {				
				if(!validateEmailAddress((String) formValue.getValue())) {
					validation = false;
					break;
				}	
			}
			else if(formValue.getId().equals("form_username")) {
				if(!validateUsername((String) formValue.getValue())) {
					validation = false;
					break;
				}
			}
			else if(formValue.getId().equals("form_scientific_area")) {	
				if(!validateScientificAreas((List<String>) formValue.getValue())) {
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
	
	private boolean validateUsername(String username) {		
		RegisteredUser user = userService.findByUsername(username);
		
		if(user == null) {
			return true;
		}
		
		return false;
	}
	
	private boolean validateEmailAddress(String email) {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		 
		Pattern pattern = Pattern.compile(regex);	
		Matcher matcher = pattern.matcher(email);
		
		RegisteredUser user = userService.findByEmail(email);
				
		if(user == null && matcher.matches()) {
			return true;
		}
		
		return false;
	}
	
	private boolean validateScientificAreas(List<String> scientificAreas) {		
		
		if(scientificAreas.size() >= 1) {
			return true;
		}
		
		return false;
	}

}
