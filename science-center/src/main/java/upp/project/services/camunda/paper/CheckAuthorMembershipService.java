package upp.project.services.camunda.paper;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Magazine;
import upp.project.model.Membership;
import upp.project.model.RegisteredUser;
import upp.project.services.MagazineService;
import upp.project.services.MembershipService;
import upp.project.services.UserService;

@Service
public class CheckAuthorMembershipService implements JavaDelegate {
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	MembershipService membershipService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | checking if author has an active membership");
		
		String chosenMagazine = (String) execution.getVariable("form_magazine");		
		Long magazineId = Long.parseLong(chosenMagazine);
		
		Magazine magazine = magazineService.findById(magazineId);
		
		String username = identityService.getCurrentAuthentication().getUserId();		
		RegisteredUser user = userService.findByUsername(username);
		
		//find active memberships for this magazine
		List<Membership> activeMemberships = membershipService.findActiveMemberships(magazine, user);
		
		if(activeMemberships.size() > 0) {
			execution.setVariable("act_membership", true);
		}
		else {
			execution.setVariable("act_membership", false);
		}
	}
}
