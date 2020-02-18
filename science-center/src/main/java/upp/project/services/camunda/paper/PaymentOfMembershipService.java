package upp.project.services.camunda.paper;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import upp.project.dtos.OrderInformationDTO;
import upp.project.dtos.OrderResponseDTO;
import upp.project.model.Magazine;
import upp.project.services.MagazineService;
import upp.project.services.UserService;

@Service
public class PaymentOfMembershipService implements JavaDelegate {
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | sending the request for payment to PaymentHub");
		
		String chosenMagazine = (String) execution.getVariable("form_magazine");		
		Long magazineId = Long.parseLong(chosenMagazine);
		
		Magazine magazine = magazineService.findById(magazineId);
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		String successUrl = "https://localhost:9997/order/success/" + execution.getProcessInstanceId() + "/" + username + "/" + magazine.getId();
		
		if(magazine != null) {
			
			OrderInformationDTO order = new OrderInformationDTO(magazine.getEmail(), magazine.getMonthlyMembershipPrice(), "USD", 
							successUrl, "https://localhost:9997/order/error", "https://localhost:9997/order/fail");	
			
			HttpEntity<OrderInformationDTO> request = new HttpEntity<>(order);
			
			//send a request to PaymentHub with order informations
			ResponseEntity<OrderResponseDTO> response = null;
			try {
				response = restTemplate.exchange("https://localhost:8762/api/client/orders/create", HttpMethod.POST, request, OrderResponseDTO.class);
			} catch (RestClientException e) {
				throw e;
			}
			
			//set the link for redirection as a process variable
			if(response != null && response.getBody().getRedirectUrl() != null) {
				execution.setVariable("redirect_link", response.getBody().getRedirectUrl());	
			}		
		}		
	}
}
