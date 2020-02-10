package upp.project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.client.RestTemplate;

import upp.project.model.RegisteredUser;
import upp.project.model.Role;
import upp.project.services.UserService;

@EnableScheduling
@SpringBootApplication
public class ScienceCenterApplication {
	
	@Autowired 
	IdentityService identityService;
	
	@Autowired
	UserService userService;
	
	public static void main(String[] args) {
		createFolderForUploads();
		SpringApplication.run(ScienceCenterApplication.class, args);
		
	}
	
    @Bean
    public RestTemplate getRestTemplate() {
    	return new RestTemplate();
    }
	
	@PostConstruct
    public void createCamundaGroupsAndUsers() { 
		//create groups
		String[] groupNames = {"admins", "reviewers", "editors", "guests", "authors"};	
		createCamundaGroups(Arrays.asList(groupNames));
		
		//create users
		List<RegisteredUser> users = userService.findByConfirmed();		
		createCamundaUsers(users);
		
		//create a guest user
		User newUser =  identityService.createUserQuery().userId("guest").singleResult();		
		if(newUser == null) {
			User camundaUser = identityService.newUser("guest");
			camundaUser.setFirstName("Guest");
			camundaUser.setLastName("Guest");
			camundaUser.setPassword("guest");
			camundaUser.setEmail("guest@gmail.com");
			identityService.saveUser(camundaUser);
		}
    }
	
	private void createCamundaGroups(List<String> groupNames) {
		
		for(String groupName : groupNames) {
			Group group = identityService.createGroupQuery().groupIdIn(groupName).singleResult();
			if(group == null) {
				Group newGroup = identityService.newGroup(groupName);
				identityService.saveGroup(newGroup);
			}
		}
	}
	
	private void createCamundaUsers(List<RegisteredUser> users) {
		for(RegisteredUser user : users) {
			User newUser =  identityService.createUserQuery().userId(user.getUsername()).singleResult();
			
			if(newUser == null) {
				User camundaUser = identityService.newUser(user.getUsername());
				camundaUser.setFirstName(user.getFirstName());
				camundaUser.setLastName(user.getLastName());
				camundaUser.setPassword(user.getPassword());
				camundaUser.setEmail(user.getEmail());
				identityService.saveUser(camundaUser);
				
				if(user.getAuthority().getRole().equals(Role.ROLE_ADMINISTRATOR)) {
					identityService.createMembership(user.getUsername(), "admins");
				}
				else if(user.getAuthority().getRole().equals(Role.ROLE_EDITOR)) {
					identityService.createMembership(user.getUsername(), "editors");
				}
				else if(user.getAuthority().getRole().equals(Role.ROLE_REVIEWER)) {
					identityService.createMembership(user.getUsername(), "reviewers");
				}
				else if(user.getAuthority().getRole().equals(Role.ROLE_AUTHOR)) {
					identityService.createMembership(user.getUsername(), "authors");
				}
			}
		}
	}
	
	private static void createFolderForUploads() {
		try {
			if(Files.exists(Paths.get("uploaded-papers"))) {
				FileSystemUtils.deleteRecursively(Paths.get("uploaded-papers").toFile());
			}
			Files.createDirectory(Paths.get("uploaded-papers"));
		}
		catch(IOException e) {
			throw new RuntimeException("There was en error while initializing storage!");
		}
	}
	
}
