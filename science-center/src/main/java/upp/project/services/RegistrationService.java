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
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
						
		List<FormValueDTO> formValues = (List<FormValueDTO>) execution.getVariable("newUserFormValues");
		
		//create and save a new user
		RegisteredUser newUser = createUser(formValues);			
		userService.save(newUser);		
		
		//send email for account confirmation
		sendConfirmationEmail(newUser);
	}
	
	private RegisteredUser createUser(List<FormValueDTO> formValues) {
		HashMap<String, String> valuesMap = new HashMap<String,String>();
		
		for(FormValueDTO value : formValues) {
			valuesMap.put(value.getId(), value.getValue());
		}
		
		//create the user
		RegisteredUser user = new RegisteredUser(valuesMap.get("form_first_name"), valuesMap.get("form_last_name"), valuesMap.get("form_city"), valuesMap.get("form_country"), 
				valuesMap.get("form_title"), valuesMap.get("form_email"), valuesMap.get("form_username"), passwordEncoder.encode(valuesMap.get("form_password")));
		
		//set user type
		if(Boolean.parseBoolean(valuesMap.get("form_reviewer"))) {
			user.setAuthority(authorityService.findByRole(Role.ROLE_REVIEWER));
		}
		else {
			user.setAuthority(authorityService.findByRole(Role.ROLE_USER));
		}
		
		//set chosen scientific areas
		String [] areas = valuesMap.get("form_scientific_area").split(",");
		
		for(String area : areas) {
			user.getScientificAreas().add(scientificAreaService.findByName(area));
		}
				
		return user;
	}
	
	private void sendConfirmationEmail(RegisteredUser user) {
		
		String confirmationLink = "http://localhost:9997/registration/confirm";
		
		String messageText = "<div style=\"text-center\"><h2>"
						   + "<a href=\"" + confirmationLink + "?username=" + user.getUsername() + "\">Click here to confirm your account</a>"
						   + "</h2></div>";
		
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