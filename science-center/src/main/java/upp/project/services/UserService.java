package upp.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.RegisteredUser;
import upp.project.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public RegisteredUser save(RegisteredUser user) {
		
		return userRepository.save(user);
	}
	
	public RegisteredUser findByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}
	
	public RegisteredUser findByEmail(String email) {
		
		return userRepository.findByEmail(email);
	}
}
