package upp.project.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import upp.project.dtos.FormDTO;
import upp.project.dtos.FormFieldDTO;
import upp.project.dtos.FormValueDTO;
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
	
	@GetMapping("/")
	public ResponseEntity<?> startRegistrationProcess() {
		
		System.out.println("Starting registration process.");
		
		//start registration process and get the first task
		String firstTaskId = processService.startProcess("user_registration"); 
		
		//get fields for the user task
		List<FormFieldDTO> frontendFields = processService.getFrontendFields(firstTaskId);
		
		//check for email and password fields
		for(FormFieldDTO field : frontendFields) {
			if(field.getId().equals("form_email")) {
				field.setEmail(true);
			}
			else if(field.getId().equals("form_password")) {
				field.setPassword(true);
			}
		}
		
		FormDTO form = new FormDTO(firstTaskId, frontendFields);
		
		return ResponseEntity.ok(form);
	}
	
	@PostMapping("/{taskId}")
	public ResponseEntity<?> register(@RequestBody List<FormValueDTO> formValues, @PathVariable String taskId) {
		
		System.out.println("Submiting registration form field values.");
		
		//save form values as a process variable
		processService.setProcessVariable(taskId, "newUserFormValues", formValues);
		
		//field validation and additional things
		for(FormValueDTO formValue : formValues) {
			if (formValue.getId().equals("form_email")) {				
				if(!validateEmailAddress(formValue.getValue())) {
					return new ResponseEntity<>("Email " + formValue.getValue() + " is invalid or already taken.", HttpStatus.BAD_REQUEST);
				}	
			}
			else if(formValue.getId().equals("form_username")) {
				if(!validateUsername(formValue.getValue())) {
					return new ResponseEntity<>("Username " + formValue.getValue() + " is not available.", HttpStatus.BAD_REQUEST);
				}
			}
			else if(formValue.getId().equals("form_scientific_area")) {				
				formValue.setValue(null);
			}
		}

		//submit form fields
		try {
			processService.submitFormFields(taskId, formValues);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
				
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/confirm")
	public ResponseEntity<?> confirmRegistration(@RequestParam String username) {
		
		System.out.println("Confirming email addres for " + username);
		
		RegisteredUser user = userService.findByUsername(username);
		
		//redirecting to an error page
		if(user == null || user.isConfirmed()) {
			HttpHeaders headersRedirect = new HttpHeaders();
			headersRedirect.add("Location", "http://localhost:4200/emailconfirmationerror");
			headersRedirect.add("Access-Control-Allow-Origin", "*");
			
			return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
		}
		
		
		user.setConfirmed(true);	
		userService.save(user);
		
		//redirecting to a success page 
		HttpHeaders headersRedirect = new HttpHeaders();
		headersRedirect.add("Location", "http://localhost:4200/emailconfirmation");
		headersRedirect.add("Access-Control-Allow-Origin", "*");
		
		return new ResponseEntity<byte[]>(null, headersRedirect, HttpStatus.FOUND);
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
	
}
