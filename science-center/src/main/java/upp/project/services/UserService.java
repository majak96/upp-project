package upp.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Authority;
import upp.project.model.Magazine;
import upp.project.model.RegisteredUser;
import upp.project.model.Role;
import upp.project.model.ScientificArea;
import upp.project.repositories.AuthorityRepository;
import upp.project.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthorityRepository authorityRepository;
	
	public RegisteredUser save(RegisteredUser user) {
		
		return userRepository.save(user);
	}
	
	public RegisteredUser findByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}
	
	public RegisteredUser findByEmail(String email) {
		
		return userRepository.findByEmail(email);
	}
	
	public List<RegisteredUser> findByRole(Role role) {
		
		Authority authority = authorityRepository.findByRole(role);
		
		return userRepository.findByAuthority(authority);
	}
	
	public List<RegisteredUser> findByScientificAreas(List<ScientificArea> scientificAreas, Role role, RegisteredUser user) {
		
		return userRepository.findByScientificAreas(scientificAreas, role, true, user);
	}
	
	public List<RegisteredUser> findByConfirmed() {
		
		return userRepository.findByConfirmed(true);
	}
	
	public List<RegisteredUser> findMagazineEditorsForScientificArea(Magazine magazine, ScientificArea scientificArea){
		
		return userRepository.findMagazineEditorsForScientificArea(magazine, scientificArea, Role.ROLE_EDITOR);
	}
	
	public List<RegisteredUser> findMagazineReviewersForScientificArea(Magazine magazine, ScientificArea scientificArea){
		
		return userRepository.findMagazineReviewersForScientificArea(magazine, scientificArea, Role.ROLE_REVIEWER);
	}
	
}
