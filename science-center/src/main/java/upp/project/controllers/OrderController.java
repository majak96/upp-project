package upp.project.controllers;

import java.util.Date;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upp.project.dtos.RedirectDTO;
import upp.project.model.Magazine;
import upp.project.model.Membership;
import upp.project.model.RegisteredUser;
import upp.project.services.MagazineService;
import upp.project.services.MembershipService;
import upp.project.services.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
	
	@Autowired
	RuntimeService runtimeService;	
	
	@Autowired
	MembershipService membershipService;
	
	@Autowired 
	UserService userService;
	
	@Autowired
	MagazineService magazineService;

	@GetMapping(value = "/success/{processInstanceId}/{username}/{magazineId}")
	public ResponseEntity<?> successOrder(@PathVariable String processInstanceId, @PathVariable String username, @PathVariable Long magazineId) {
		
		System.out.println("ORD | success");
		
		Magazine magazine = magazineService.findById(magazineId);
		RegisteredUser user = userService.findByUsername(username);
		
		if(magazine != null && user != null) {
			//membership lasts for a month
			Date date = new Date();
			DateTime originalDateTime = new DateTime(date);
			DateTime endDateTime = originalDateTime.plusMonths(1);
						
			Membership membership = new Membership(user, magazine, endDateTime.toDate());
			membershipService.save(membership);
			
			MessageCorrelationResult results = runtimeService.createMessageCorrelation("PaymentSuccess")
			          .processInstanceId(processInstanceId).correlateWithResult();
			
			RedirectDTO redirectDTO = new RedirectDTO();
			redirectDTO.setUrl("https://localhost:4206/paymentsuccess/");
			return ResponseEntity.ok(redirectDTO);
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping(value = "/error")
	public ResponseEntity<?> errorOrder() {
		System.out.println("ORD | error");
		
		RedirectDTO redirectDTO = new RedirectDTO();
		redirectDTO.setUrl("https://localhost:4206/paymenterror/");
		return ResponseEntity.ok(redirectDTO);
	}
	
	@GetMapping(value = "/fail")
	public ResponseEntity<?> failOrder() {
		System.out.println("ORD | fail");
		
		RedirectDTO redirectDTO = new RedirectDTO();
		redirectDTO.setUrl("https://localhost:4206/paymentfail/");
		return ResponseEntity.ok(redirectDTO);
	}
}
