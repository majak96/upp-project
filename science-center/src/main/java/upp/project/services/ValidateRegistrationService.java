package upp.project.services;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;
import upp.project.model.RegisteredUser;

@Service
public class ValidateRegistrationService implements JavaDelegate{
	
	@Autowired
	UserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("Validation of registration data");
		
		boolean validation = true; 
		
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("newUserFormValues");
		
		HashMap<String, String> valuesMap = new HashMap<String,String>();
		
		for(FormValueDTO value : formValues) {
			valuesMap.put(value.getId(), value.getValue());
		}
		
		for(FormValueDTO formValue : formValues) {
			if (formValue.getId().equals("form_email")) {				
				if(!validateEmailAddress(formValue.getValue())) {
					validation = false;
				}	
			}
			else if(formValue.getId().equals("form_username")) {
				if(!validateUsername(formValue.getValue())) {
					validation = false;
				}
			}
			else if(formValue.getId().equals("form_scientific_area")) {	
				if(!validateScientificAreas(formValue.getValue())) {
					validation = false;
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
	
	private boolean validateScientificAreas(String scientificAreas) {		
		String[] scientificAreasArray = scientificAreas.split(",");
		
		if(scientificAreasArray.length >= 1) {
			return true;
		}
		
		return false;
	}

}
