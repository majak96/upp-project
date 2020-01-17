package upp.project.services;

import java.util.HashMap;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import upp.project.dtos.FormValueDTO;
import upp.project.model.RegisteredUser;
import upp.project.model.Role;

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
    private JavaMailSender javaMailSender;
	
	@Autowired
	private ProcessService processService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("Saving registration and sending mail");
						
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("newUserFormValues");
		
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
		HashMap<String, String> valuesMap = new HashMap<String,String>();
		
		for(FormValueDTO value : formValues) {
			valuesMap.put(value.getId(), value.getValue());
		}
		
		//create the user
		RegisteredUser user = new RegisteredUser(valuesMap.get("form_first_name"), valuesMap.get("form_last_name"), valuesMap.get("form_city"), valuesMap.get("form_country"), 
				valuesMap.get("form_title"), valuesMap.get("form_email"), valuesMap.get("form_username"), passwordEncoder.encode(valuesMap.get("form_password")));

		//set user type - all users are regular users in the beginning
		user.setAuthority(authorityService.findByRole(Role.ROLE_USER));
		
		//set chosen scientific areas
		String [] areas = valuesMap.get("form_scientific_area").split(",");
		
		for(String area : areas) {
			user.getScientificAreas().add(scientificAreaService.findByName(area));
		}
						
		return user;
	}
	
	private void sendConfirmationEmail(RegisteredUser user, String processInstanceId) { 
		
		String confirmationLinkBase = "http://localhost:9997/registration/confirm";		
		String confirmationLink = confirmationLinkBase + "?username=" + user.getUsername() + "&processInstanceId=" + processInstanceId;
				
		String messageText = "<div style=\"text-center\">"
						   + "<h2>"
						   + "<a href=\"" + confirmationLink + "\">Click here to confirm your account</a>"
						   + "</h2>"
						   + "</div>";
		
        MimeMessage mail = javaMailSender.createMimeMessage();

		try {          		
	        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
	        
	        helper.setTo(user.getEmail());
	        helper.setSubject("ScienceCenter Account Confirmation");
	        helper.setText(messageText, true);
	        	        
            javaMailSender.send(mail);
		}
		catch(Exception e) {
			
		}
    }

}
