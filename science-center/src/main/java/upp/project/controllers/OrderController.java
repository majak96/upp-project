package upp.project.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upp.project.dtos.RedirectDTO;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

	@GetMapping(value = "/success")
	public ResponseEntity<?> successOrder() {
		
		RedirectDTO redirectDTO = new RedirectDTO();
		redirectDTO.setUrl("https://localhost:4200/paymentsuccess/");
		return ResponseEntity.ok(redirectDTO);
	}
	
	@GetMapping(value = "/error")
	public ResponseEntity<?> errorOrder() {
		
		RedirectDTO redirectDTO = new RedirectDTO();
		redirectDTO.setUrl("https://localhost:4200/paymenterror/");
		return ResponseEntity.ok(redirectDTO);
	}
	
	@GetMapping(value = "/fail")
	public ResponseEntity<?> failOrder() {
		
		RedirectDTO redirectDTO = new RedirectDTO();
		redirectDTO.setUrl("https://localhost:4200/paymentfail/");
		return ResponseEntity.ok(redirectDTO);
	}
}
