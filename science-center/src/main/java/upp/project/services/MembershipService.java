package upp.project.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Magazine;
import upp.project.model.Membership;
import upp.project.model.RegisteredUser;
import upp.project.repositories.MembershipRepository;

@Service
public class MembershipService {

	@Autowired
	MembershipRepository membershipRepository;
	
	public Membership save(Membership membership) {
		
		return membershipRepository.save(membership);
	}
	
	public List<Membership> findActiveMemberships(Magazine magazine, RegisteredUser user) {
		
		return membershipRepository.findByUserAndMagazineAndValidUntilAfter(user, magazine, new Date());
	}

}
