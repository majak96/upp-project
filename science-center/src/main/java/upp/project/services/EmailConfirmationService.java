package upp.project.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.RegisteredUser;

@Service
public class EmailConfirmationService implements JavaDelegate{
		
	@Autowired
	UserService userService;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("Saving email confirmation");
						
		String username = (String) execution.getVariable("registrationUsername");
				
		RegisteredUser user = userService.findByUsername(username);
		
		if(user != null) {
			user.setConfirmed(true);
			userService.save(user);
		}	
	}
}
