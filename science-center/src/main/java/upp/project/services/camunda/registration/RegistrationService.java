package upp.project.services.camunda.registration;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;
import upp.project.model.RegisteredUser;
import upp.project.model.Role;
import upp.project.services.AuthorityService;
import upp.project.services.EmailService;
import upp.project.services.ScientificAreaService;
import upp.project.services.UserService;

@Service
public class RegistrationService implements JavaDelegate{
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthorityService authorityService;
		
	@Autowired
	ScientificAreaService scientificAreaService;
	
	@Autowired 
	EmailService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("REG | saving registration data and sending a confirmation email");
						
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("formData");
		
		//create and save a new user
		RegisteredUser newUser = createUser(formValues);			
		userService.save(newUser);		
		
		//save username as process variable
		execution.setVariable("registrationUsername", newUser.getUsername());
		
		String salt = BCrypt.gensalt();
        String hashedValue = BCrypt.hashpw(newUser.getUsername(), salt);
        
        execution.setVariable("hashedValue", hashedValue);
		
		//send email for account confirmation
		sendConfirmationEmail(newUser, execution.getProcessInstance().getId());
		
	}
	
	private RegisteredUser createUser(List<FormValueDTO> formValues) {
		HashMap<String, Object> valuesMap = new HashMap<String, Object>();
		
		for(FormValueDTO value : formValues) {
			valuesMap.put(value.getId(), value.getValue());
		}
		
		//create the user
		RegisteredUser user = new RegisteredUser((String) valuesMap.get("form_first_name"), (String) valuesMap.get("form_last_name"), (String) valuesMap.get("form_city"), 
												 (String) valuesMap.get("form_country"), (String) valuesMap.get("form_title"), (String) valuesMap.get("form_email"), 
												 (String) valuesMap.get("form_username"), passwordEncoder.encode((String) valuesMap.get("form_password")));

		//set user type - all users are regular users (authors) in the beginning
		user.setAuthority(authorityService.findByRole(Role.ROLE_AUTHOR));
		
		//set chosen scientific areas
		List<String> areas = (List<String>) valuesMap.get("form_scientific_area");		
		for(String area : areas) {
			user.getScientificAreas().add(scientificAreaService.findByName(area));
		}
						
		return user;
	}
	
	private void sendConfirmationEmail(RegisteredUser user, String processInstanceId) { 
		
		String confirmationLinkBase = "https://localhost:9997/registration/confirm";		
		String confirmationLink = confirmationLinkBase + "?username=" + user.getUsername() + "&processInstanceId=" + processInstanceId;
		
		String messageText = "<div>"
				   + "<p>"
				   + "Dear " + user.getFirstName() + " " + user.getLastName() + ", <br><br>"
				   + "Please click <a href=\"" + confirmationLink + "\">here</a> to confirm your email adress. <br><br>"
				   + "Best regards, <br>"
				   + "ScienceCenter"
				   + "<p>"
				   + "</div>";
		
		emailService.sendEmail(user.getEmail(), "ScienceCenter Email Confirmation", messageText);
    }

}
