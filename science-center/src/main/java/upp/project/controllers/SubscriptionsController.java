package upp.project.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import upp.project.dtos.OrderResponseDTO;
import upp.project.dtos.RedirectDTO;
import upp.project.dtos.SubscriptionInformationDTO;
import upp.project.model.Magazine;
import upp.project.model.OrderStatus;
import upp.project.model.RegisteredUser;
import upp.project.model.UserSubscription;
import upp.project.services.MagazineService;
import upp.project.services.UserService;
import upp.project.services.UserSubscriptionService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/subscription", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubscriptionsController {
	
	@Autowired
	private MagazineService magazineService;
	
	@Autowired
	private UserSubscriptionService userSubscriptionService;

	@Autowired
	private UserService userService;

	@Autowired
	private RestTemplate restTemplate;

	@Value("https://localhost:9997/subscription/success/")
	private String successUrl;

	@Value("https://localhost:9997/subscription/fail/")
	private String failedUrl;

	@Value("https://localhost:9997/subscription/error/")
	private String errorUrl;

	@Value("https://localhost:8762/api/client/subscription/create")
	private String kpUrl;

	@PostMapping("/create/{magazineId}")
	public ResponseEntity<?> createSubscription(Principal principal, @PathVariable Long magazineId) {
		
		//check if logged in
		if(principal == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		RegisteredUser user = userService.findByUsername(principal.getName());
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		//check if magazine exists
		Magazine magazine = magazineService.findById(magazineId);
		if (magazine == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		UserSubscription userSubscription = new UserSubscription(OrderStatus.INITIATED, magazine, user);
		userSubscription = userSubscriptionService.save(userSubscription);
		if (userSubscription == null) {
			return ResponseEntity.status(500).build();
		}

		SubscriptionInformationDTO subscriptionInformationDTO = new SubscriptionInformationDTO(userSubscription, successUrl + Long.toString(userSubscription.getId()), 
				errorUrl + Long.toString(userSubscription.getId()), failedUrl + Long.toString(userSubscription.getId()));

		HttpEntity<SubscriptionInformationDTO> request = new HttpEntity<>(subscriptionInformationDTO);

		ResponseEntity<OrderResponseDTO> response = null;
		try {
			response = restTemplate.exchange(this.kpUrl, HttpMethod.POST, request, OrderResponseDTO.class);
			userSubscription.setSubscriptionStatus(OrderStatus.CREATED);
			
		} catch (RestClientException e) {
			userSubscription.setSubscriptionStatus(OrderStatus.INVALID);
			userSubscriptionService.save(userSubscription);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		userSubscriptionService.save(userSubscription);

		RedirectDTO redirectDTO = new RedirectDTO();
		redirectDTO.setUrl(response.getBody().getRedirectUrl());
		return ResponseEntity.ok(redirectDTO);
	}
	
	@GetMapping(value = "/error/{subscriptionId}")
	public ResponseEntity<?> errorSubscription(@PathVariable Long subscriptionId) {
		System.out.println("SUB | error");
		
		UserSubscription subscription = userSubscriptionService.findById(subscriptionId);
		subscription.setSubscriptionStatus(OrderStatus.INVALID);
		userSubscriptionService.save(subscription);
		
		RedirectDTO redirectDTO = new RedirectDTO();
		redirectDTO.setUrl("https://localhost:4206/paymenterror/");
		return ResponseEntity.ok(redirectDTO);
	}
	
	@GetMapping(value = "/success/{subscriptionId}")
	public ResponseEntity<?> successSubscription(@PathVariable Long subscriptionId) {
		System.out.println("ORD | success");
		
		UserSubscription subscription = userSubscriptionService.findById(subscriptionId);
		subscription.setSubscriptionStatus(OrderStatus.COMPLETED);
		userSubscriptionService.save(subscription);
		
		RedirectDTO redirectDTO = new RedirectDTO();
		redirectDTO.setUrl("https://localhost:4206/paymentsuccess/");
		return ResponseEntity.ok(redirectDTO);
	}
	
	@GetMapping(value = "/fail/{subscriptionId}")
	public ResponseEntity<?> failSubscription(@PathVariable Long subscriptionId) {
		System.out.println("ORD | fail");
		
		UserSubscription subscription = userSubscriptionService.findById(subscriptionId);
		subscription.setSubscriptionStatus(OrderStatus.CANCELED);
		userSubscriptionService.save(subscription);
		
		RedirectDTO redirectDTO = new RedirectDTO();
		redirectDTO.setUrl("https://localhost:4206/paymentfail/");
		return ResponseEntity.ok(redirectDTO);
	}
}
