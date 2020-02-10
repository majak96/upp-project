package upp.project.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upp.project.dtos.UserDTO;
import upp.project.model.Authority;
import upp.project.model.RegisteredUser;
import upp.project.model.Role;
import upp.project.model.ScientificArea;
import upp.project.services.AuthorityService;
import upp.project.services.ScientificAreaService;
import upp.project.services.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthorityService authorityService;
	
	@Autowired
	ScientificAreaService scientificAreaService;
	
	@Autowired
	IdentityService identityService;
	
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@GetMapping("/{role}")
	public ResponseEntity<?> getRegisteredUsers(@PathVariable Role role) {
		
		List<RegisteredUser> foundUsers = userService.findByRole(role);
		List<UserDTO> dtos = mapToDTO(foundUsers);
		
		return ResponseEntity.ok(dtos);
	}
	
	@PostMapping("")
	public ResponseEntity<?> addRegisteredUser(@RequestBody @Valid UserDTO userDTO) {
		
		Authority authority = authorityService.findByRole(userDTO.getRole());
		
		if(authority == null || userDTO.getRole().equals(Role.ROLE_AUTHOR)) {
			return new ResponseEntity<>("Role doesn't exist!", HttpStatus.BAD_REQUEST);
		}
		
		if(!validateUsername(userDTO.getUsername())) {
			return new ResponseEntity<>("A user with this username already exists!", HttpStatus.BAD_REQUEST);
		}
		else if (!validateEmailAddress(userDTO.getEmail())) {
			return new ResponseEntity<>("A user with this email address already exists!", HttpStatus.BAD_REQUEST);
		}
		else if (!validateScientificAreas(userDTO.getScientificAreas(), userDTO.getRole())) {
			return new ResponseEntity<>("There should be at least one scientific area selected!", HttpStatus.BAD_REQUEST);
		}
		
		List<ScientificArea> scientificAreas = new ArrayList<ScientificArea>();
		for(Long id : userDTO.getScientificAreas()) {
			ScientificArea sciArea = scientificAreaService.findById(id);
			if(sciArea != null) {
				scientificAreas.add(sciArea);
			}		
		}
		
		userDTO.setPassword(passwordEncoder().encode(userDTO.getPassword()));
		
		RegisteredUser user = new RegisteredUser(userDTO, authority, scientificAreas);		
		RegisteredUser savedUser = userService.save(user);
		
		if(savedUser == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		//create a new user in camunda
		User camundaUser = identityService.newUser(user.getUsername());
		camundaUser.setFirstName(user.getFirstName());
		camundaUser.setLastName(user.getLastName());
		camundaUser.setPassword(user.getPassword());
		camundaUser.setEmail(user.getEmail());
		
		try {
			identityService.saveUser(camundaUser);
			
			String camundaGroupName = user.getAuthority().getRole().equals(Role.ROLE_EDITOR) ? "editors" : "admins";
			identityService.createMembership(user.getUsername(), camundaGroupName);		
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		return ResponseEntity.ok().build();
	}
	
	private boolean validateUsername(String username) {		
		RegisteredUser user = userService.findByUsername(username);
		
		if(user == null) {
			return true;
		}
		
		return false;
	}
	
	private boolean validateEmailAddress(String email) {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		 
		Pattern pattern = Pattern.compile(regex);	
		Matcher matcher = pattern.matcher(email);
		
		RegisteredUser user = userService.findByEmail(email);
				
		if(user == null && matcher.matches()) {
			return true;
		}
		
		return false;
	}
	
	private boolean validateScientificAreas(List<Long> scientificAreas, Role role) {		
		
		if(role.equals(Role.ROLE_EDITOR) && scientificAreas.size() < 1) {
			return false;
		}
		
		return true;
	}
	
	private List<UserDTO> mapToDTO(List<RegisteredUser> users){
		
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		
		for(RegisteredUser user : users) {
			dtos.add(new UserDTO(user));
		}
		
		return dtos;
	}
	
}
