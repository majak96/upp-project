package upp.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import upp.project.model.RegisteredUser;
import upp.project.repositories.UserRepository;

@Service
public class UserPrincipalDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		RegisteredUser user = userRepository.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User with the username " + username + " doesn't exist.");
		}
		
		return new UserPrincipal(user);
	}
	
	

}
