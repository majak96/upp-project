package upp.project.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.RegisteredUser;
import upp.project.model.Role;

@Service
public class SaveReviewerRoleService implements JavaDelegate{
		
	@Autowired
	UserService userService;
	
	@Autowired
	AuthorityService authorityService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("Saving reviewer role");
						
		String username = (String) execution.getVariable("registrationUsername");
		Boolean reviewerConfirmation = (Boolean) execution.getVariable("reviewer_confirm");
			
		//check if admin confirmed
		if(reviewerConfirmation) {
			RegisteredUser user = userService.findByUsername(username);
			
			//change user role to ROLE_REVIEWER
			if(user != null) {
				user.setAuthority(authorityService.findByRole(Role.ROLE_REVIEWER));
				userService.save(user);
			}
		}	
	}
}
