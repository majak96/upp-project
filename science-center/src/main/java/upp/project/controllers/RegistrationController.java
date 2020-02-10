package upp.project.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import upp.project.dtos.FormDTO;
import upp.project.dtos.FormFieldDTO;
import upp.project.dtos.FormValueDTO;
import upp.project.dtos.SubmitResponseDTO;
import upp.project.model.RegisteredUser;
import upp.project.services.ProcessService;
import upp.project.services.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	ProcessService processService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@GetMapping("/")
	public ResponseEntity<?> startRegistrationProcess() {
		
		System.out.println("REG | starting registration process.");
		
		identityService.setAuthenticatedUserId("guest");
		identityService.setAuthentication("guest", Collections.singletonList("guests"));
		
		//start registration process and get the first task
		Task firstTask = processService.startProcess("user_registration"); 
				
		//get fields for the user task
		List<FormFieldDTO> frontendFields = processService.getFrontendFields(firstTask.getId());
		
		FormDTO form = new FormDTO(firstTask.getId(), firstTask.getProcessInstanceId(), firstTask.getName(), frontendFields);
		
		return ResponseEntity.ok(form);
	}
		
	@GetMapping(value = "/confirm")
	public ResponseEntity<?> confirmRegistration(@RequestParam String username, @RequestParam String processInstanceId) {
		
		System.out.println("REG | confirming registration");
		
		identityService.setAuthenticatedUserId("guest");
		identityService.setAuthentication("guest", Collections.singletonList("guests"));
		
		if(!processService.processInstanceExists(processInstanceId)) {
			HttpHeaders headersRedirect = new HttpHeaders();
			headersRedirect.add("Location", "https://localhost:4206/emailconfirmationerror");
			headersRedirect.add("Access-Control-Allow-Origin", "*");
			
			return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
		}
		
		
		String hashedValue = (String) processService.getProcessVariable(processInstanceId, "hashedValue");		
		boolean result = BCrypt.checkpw(username, hashedValue);
		
		if(!result) {
			HttpHeaders headersRedirect = new HttpHeaders();
			headersRedirect.add("Location", "https://localhost:4206/emailconfirmationerror");
			headersRedirect.add("Access-Control-Allow-Origin", "*");
			
			return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
		}
		
		RegisteredUser user = userService.findByUsername(username);
		
		//if user doesn't exist or has already confirmed his registration
		//redirecting to the error page
		if(user == null || user.isConfirmed()) {
			HttpHeaders headersRedirect = new HttpHeaders();
			headersRedirect.add("Location", "https://localhost:4206/emailconfirmationerror");
			headersRedirect.add("Access-Control-Allow-Origin", "*");
			
			return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
		}
		
		//get the next task - for email confirmation
		String taskId = processService.getNextTaskId(processInstanceId);
		
		List<FormValueDTO> formValues= new ArrayList<FormValueDTO>();
		formValues.add(new FormValueDTO("email_confirm", true));
		
		//submits the user task for email confirmation
		try {
			processService.submitFormFields(taskId, formValues);
		}
		catch(Exception e) {
			//redirecting to the error page
			HttpHeaders headersRedirect = new HttpHeaders();
			headersRedirect.add("Location", "http://localhost:4206/emailconfirmationerror");
			headersRedirect.add("Access-Control-Allow-Origin", "*");
			
			return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
		}
			
		//redirecting to a success page 
		HttpHeaders headersRedirect = new HttpHeaders();
		headersRedirect.add("Location", "http://localhost:4206/emailconfirmation");
		headersRedirect.add("Access-Control-Allow-Origin", "*");
		
		return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
	}
	
}
