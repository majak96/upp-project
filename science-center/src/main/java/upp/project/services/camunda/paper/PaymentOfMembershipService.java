package upp.project.services.camunda.paper;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import upp.project.dtos.OrderInformationDTO;
import upp.project.dtos.OrderResponseDTO;
import upp.project.model.Magazine;
import upp.project.services.MagazineService;

@Service
public class PaymentOfMembershipService implements JavaDelegate {
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("PAP | sending the order to PaymentHub");
		
		String chosenMagazine = (String) execution.getVariable("form_magazine");		
		Long magazineId = Long.parseLong(chosenMagazine);
		
		Magazine magazine = magazineService.findById(magazineId);
		
		if(magazine != null) {
			
			/*OrderInformationDTO order = new OrderInformationDTO(magazine.getEmail(), magazine.getMonthlyMembershipPrice(), "USD", 
					"https://localhost:9997/order/success", "https://localhost:9997/order/error", "https://localhost:9997/order/fail");	
			
			HttpEntity<OrderInformationDTO> request = new HttpEntity<>(order);
			
			ResponseEntity<OrderResponseDTO> response = null;
			try {
				response = restTemplate.exchange("https://localhost:8762/api/client/orders/create", HttpMethod.POST, request, OrderResponseDTO.class);
			} catch (RestClientException e) {
				throw e;
			}*/
					
			execution.setVariable("redirect_link", "https://localhost:4203/success");			
		}
		
	}
}
