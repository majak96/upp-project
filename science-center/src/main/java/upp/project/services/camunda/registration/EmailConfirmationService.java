package upp.project.services.camunda.registration;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.RegisteredUser;
import upp.project.services.UserService;

@Service
public class EmailConfirmationService implements JavaDelegate{
		
	@Autowired
	UserService userService;
	
	@Autowired
	IdentityService identityService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("REG | saving email confirmation");
						
		String username = (String) execution.getVariable("registrationUsername");
				
		RegisteredUser user = userService.findByUsername(username);
		
		if(user != null) {
		
			//create a new user in camunda
			User camundaUser = identityService.newUser(user.getUsername());
			camundaUser.setFirstName(user.getFirstName());
			camundaUser.setLastName(user.getLastName());
			camundaUser.setPassword(user.getPassword());
			camundaUser.setEmail(user.getEmail());
			
			try {
				identityService.saveUser(camundaUser);
				identityService.createMembership(user.getUsername(), "authors");
			}
			catch(Exception e) {
				throw e;
			}
			
			user.setConfirmed(true);
			userService.save(user);
		}	
	}
}
