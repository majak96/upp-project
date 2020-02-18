package upp.project.services.camunda.magazine;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import upp.project.dtos.RegistrationDTO;
import upp.project.dtos.RegistrationResponseDTO;
import upp.project.model.Magazine;
import upp.project.services.MagazineService;

@Service
public class RegisterMagazineToPaymentHub implements JavaDelegate{
	
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long magazineId = (Long) execution.getVariable("magazineId");

		Magazine magazine = magazineService.findById(magazineId);
						
		if(magazine != null) {
			String successUrl = "https://localhost:9997/magazine/registration/" + execution.getProcessInstanceId() + "/" + magazine.getId();
			
			RegistrationDTO registrationDTO = new RegistrationDTO(magazine.getEmail(), magazine.getName(), successUrl);	
	
			HttpEntity<RegistrationDTO> request = new HttpEntity<>(registrationDTO);
				
			//send a request to PaymentHub with information about the magazine
			ResponseEntity<RegistrationResponseDTO> response = null;
			try {
				response = restTemplate.exchange("https://localhost:8762/api/client/seller", HttpMethod.POST, request, RegistrationResponseDTO.class);
			} 
			catch (RestClientException e) {
				throw e;
			}
			
			//set the link for redirection as a process variable
			if(response != null && response.getBody().getResponseUrl() != null) {
				execution.setVariable("redirect_link", response.getBody().getResponseUrl());	
			}					
		}		
	}

}
