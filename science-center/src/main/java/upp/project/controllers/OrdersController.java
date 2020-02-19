package upp.project.controllers;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import upp.project.dtos.OrderInformationDTO;
import upp.project.dtos.OrderResponseDTO;
import upp.project.dtos.RedirectDTO;
import upp.project.dtos.UserOrderDTO;
import upp.project.dtos.UserPurchasedItemsDTO;
import upp.project.model.Issue;
import upp.project.model.IssueOrder;
import upp.project.model.Magazine;
import upp.project.model.MagazineOrder;
import upp.project.model.OrderStatus;
import upp.project.model.Paper;
import upp.project.model.PaperOrder;
import upp.project.model.RegisteredUser;
import upp.project.model.UserOrder;
import upp.project.model.UserSubscription;
import upp.project.services.IssueService;
import upp.project.services.MagazineService;
import upp.project.services.PaperService;
import upp.project.services.UserOrderService;
import upp.project.services.UserService;
import upp.project.services.UserSubscriptionService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrdersController {

	@Autowired
	private UserOrderService userOrderService;
	
	@Autowired
	private UserSubscriptionService userSubscriptionService;
		
	@Autowired
	private MagazineService magazineServicce;
	
	@Autowired
	private IssueService issueServicce;
	
	@Autowired
	private PaperService paperService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping("/create")
	public ResponseEntity<?> createOrder(Principal principal, @RequestBody UserOrderDTO orderDTO) {
		UserOrder userOrder = null;
	
		//check if logged in
		if(principal == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		RegisteredUser user = userService.findByUsername(principal.getName());	
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		//if list is empty
		if(orderDTO.getIds().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		if(orderDTO.getType().equals("magazine")) {
			//check if magazine exists
			Magazine magazine = this.magazineServicce.findById(orderDTO.getIds().get(0));
			if(magazine == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			userOrder = new MagazineOrder(magazine);
			userOrder.setEmail(magazine.getEmail());
			userOrder.setPaymentAmount(magazine.getMonthlyMembershipPrice());		
		} 
		else if(orderDTO.getType().equals("issue")) {
			Set<Issue> issues = new HashSet<Issue>();
			Magazine magazine = null;
			
			for(Long id : orderDTO.getIds()) {
				//check if issue exists
				Issue issue = this.issueServicce.findById((id));
				if(issue == null)
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				
				if(magazine == null)
					magazine = issue.getMagazine();
				
				issues.add(issue);
			}
			
			userOrder = new IssueOrder(issues);
			userOrder.setEmail(magazine.getEmail());
			userOrder.setPaymentAmount(issues.size() * magazine.getIssuePrice());
			
			
		} else {
			Set<Paper> papers = new HashSet<Paper>();
			Magazine magazine = null;
			
			for(Long id : orderDTO.getIds()) {
				Paper paper = this.paperService.findById(id);
				
				if(paper == null)
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				
				if(magazine == null)
					magazine = paper.getIssue().getMagazine();
				
				papers.add(paper);
			} 
			
			userOrder = new PaperOrder(papers);
			userOrder.setEmail(magazine.getEmail());
			userOrder.setPaymentAmount(papers.size() * magazine.getPaperPrice());
		}
		
		userOrder.setBuyer(user);
		userOrder.setOrderStatus(OrderStatus.CREATED);
		userOrder.setPaymentCurrency("USD");

		userOrder = this.userOrderService.save(userOrder);
		if (userOrder == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		OrderInformationDTO orderInformationDTO = new OrderInformationDTO();
		orderInformationDTO.setPaymentCurrency("USD");
		orderInformationDTO.setSuccessUrl("https://localhost:9997/orders/success"+ "?id=" + userOrder.getId() + "&type=" + orderDTO.getType());
		orderInformationDTO.setFailedUrl("https://localhost:9997/orders/failed"+ "?id=" + userOrder.getId() + "&type=" + orderDTO.getType());
		orderInformationDTO.setErrorUrl("https://localhost:9997/orders/error"+ "?id=" + userOrder.getId() + "&type=" + orderDTO.getType());
		orderInformationDTO.setOrderId(userOrder.getId());
		orderInformationDTO.setEmail(userOrder.getEmail());
		orderInformationDTO.setPaymentAmount(userOrder.getPaymentAmount());

		HttpEntity<OrderInformationDTO> request = new HttpEntity<>(orderInformationDTO);

		ResponseEntity<OrderResponseDTO> response = null;
		try {
			response = restTemplate.exchange("https://localhost:8762/api/client/orders/create", HttpMethod.POST, request, OrderResponseDTO.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			userOrder.setOrderStatus(OrderStatus.INVALID);
			return ResponseEntity.status(400).body("Greska prilikom kontaktiranja kpa");
		}
		
		RedirectDTO redirectDTO = new RedirectDTO();
		redirectDTO.setUrl(response.getBody().getRedirectUrl());
		return ResponseEntity.ok(redirectDTO);
	}
	
	@GetMapping("/success")
	public ResponseEntity<?> successfulOrder(@RequestParam("id") Long id, @RequestParam("type") String type, @RequestParam("processId") Optional<String> processId ) {
				
		UserOrder userOrder = null;
		if(type.equals("magazine")) {
			userOrder = this.userOrderService.getMagazineOrder(id);
		} else if(type.equals("issue")) {
			userOrder = this.userOrderService.getIssueOrder(id);
		} else {
			userOrder = this.userOrderService.getPaperOrder(id);
		}
		
		RedirectDTO redirectDTO = new RedirectDTO();
		redirectDTO.setUrl("https://localhost:4206/paymentsuccess/");
		
		if(userOrder == null) {
			redirectDTO.setUrl("https://localhost:4206/paymenterror/");
			return ResponseEntity.ok(redirectDTO);
		}
		
		userOrder.setOrderStatus(OrderStatus.COMPLETED);
		userOrderService.save(userOrder);
		
		return ResponseEntity.ok(redirectDTO);	
	}
	
	@GetMapping("/failed")
	public ResponseEntity<?> failedOrder(@RequestParam("id") Long id, @RequestParam("type") String type, @RequestParam("processId") Optional<String> processId) {
				
		UserOrder userOrder = null;
		if(type.equals("magazine")) {
			userOrder = this.userOrderService.getMagazineOrder(id);
		} else if(type.equals("issue")) {
			userOrder = this.userOrderService.getIssueOrder(id);
		} else {
			userOrder = this.userOrderService.getPaperOrder(id);
		}
		
		RedirectDTO redirectDTO = new RedirectDTO();
		redirectDTO.setUrl("https://localhost:4206/paymentfail");
		
		if(userOrder == null) {
			redirectDTO.setUrl("https://localhost:4206/paymentfail");
			return ResponseEntity.ok(redirectDTO);
		}
		
		userOrder.setOrderStatus(OrderStatus.INVALID);
		userOrderService.save(userOrder);
			
		return ResponseEntity.ok(redirectDTO);	
	}
	
	@GetMapping("/error")
	public ResponseEntity<?> errorOrder(@RequestParam("id") Long id, @RequestParam("type") String type, @RequestParam("processId") Optional<String> processId) {
		
		UserOrder userOrder = null;
		if(type.equals("magazine")) {
			userOrder = this.userOrderService.getMagazineOrder(id);
		} else if(type.equals("issue")) {
			userOrder = this.userOrderService.getIssueOrder(id);
		} else {
			userOrder = this.userOrderService.getPaperOrder(id);
		}
		RedirectDTO redirectDTO = new RedirectDTO();
		redirectDTO.setUrl("https://localhost:4206/paymenterror");
		
		if(userOrder == null) {
			return ResponseEntity.ok(redirectDTO);
		}
		
		userOrder.setOrderStatus(OrderStatus.CANCELED);
		userOrderService.save(userOrder);
		
		return ResponseEntity.ok(redirectDTO);		
	}
	
	@GetMapping("/user")
	public ResponseEntity<?> getUserOrders(Principal principal) {
		//check if logged in
		if(principal == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		RegisteredUser user = userService.findByUsername(principal.getName());	
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		List<Magazine> magazines = userOrderService.getAllPurchasedMagazines(user);
		List<Issue> issues = userOrderService.getAllPurchasedIssues(user);
		List<Paper> papers = userOrderService.getAllPurchasedPapers(user);
		List<UserSubscription> subscriptions = userSubscriptionService.getAllUserSubscriptions(user);
		
		UserPurchasedItemsDTO dto = new UserPurchasedItemsDTO(magazines, issues, papers, subscriptions);
		return ResponseEntity.ok(dto);
	}
}
