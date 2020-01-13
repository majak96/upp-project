package upp.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Authority;
import upp.project.model.Role;
import upp.project.repositories.AuthorityRepository;

@Service
public class AuthorityService {

	@Autowired
	AuthorityRepository authorityRepository;
	
	public Authority findByRole(Role role) {
		
		return authorityRepository.findByRole(role);
	}

}
