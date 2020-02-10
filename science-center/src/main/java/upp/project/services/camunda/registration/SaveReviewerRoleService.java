package upp.project.services.camunda.registration;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.RegisteredUser;
import upp.project.model.Role;
import upp.project.services.AuthorityService;
import upp.project.services.UserService;

@Service
public class SaveReviewerRoleService implements JavaDelegate{
		
	@Autowired
	UserService userService;
	
	@Autowired
	AuthorityService authorityService;
	
	@Autowired
	IdentityService identityService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("REG | saving reviewer role");
						
		String username = (String) execution.getVariable("registrationUsername");
		Boolean reviewerConfirmation = (Boolean) execution.getVariable("reviewer_confirm");
			
		//check if admin confirmed
		if(reviewerConfirmation) {
			RegisteredUser user = userService.findByUsername(username);
			
			//change user role to ROLE_REVIEWER
			if(user != null) {
				user.setAuthority(authorityService.findByRole(Role.ROLE_REVIEWER));
				userService.save(user);
				
				identityService.deleteMembership(user.getUsername(), "authors");
				identityService.createMembership(user.getUsername(), "reviewers");
			}
		}	
	}
}
