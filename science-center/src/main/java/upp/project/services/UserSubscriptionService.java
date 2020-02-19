package upp.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import upp.project.dtos.OrderStatusInformationDTO;
import upp.project.model.OrderStatus;
import upp.project.model.UserSubscription;
import upp.project.repositories.UserSubscriptionRepository;

@Service
public class UserSubscriptionService {

	@Autowired
	UserSubscriptionRepository userSubscriptionRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	private String kpUrl = "https://localhost:8762/api/client/subscription/status";
	
	public UserSubscription save(UserSubscription userSubscription) {
		
		return userSubscriptionRepository.save(userSubscription);
	}
	
	public UserSubscription findById(Long id) {
		
		return userSubscriptionRepository.getOne(id);
	}
	
	public List<UserSubscription> getSubscriptionForSync() {
		List<OrderStatus> statusList = new ArrayList<OrderStatus>();
		statusList.add(OrderStatus.CREATED);
		statusList.add(OrderStatus.COMPLETED);
		
		return userSubscriptionRepository.findBySubscriptionStatusIn(statusList);
	}
	
	@Scheduled(initialDelay = 10000, fixedRate = 180000)
	public void checkSubscriptionStatus() {

		//find subscriptions
		List<UserSubscription> subscriptions = getSubscriptionForSync();
		
		for (UserSubscription userSubscription : subscriptions) {
			ResponseEntity<OrderStatusInformationDTO> response = null;
			
			try {
				response = restTemplate.getForEntity(this.kpUrl + "?subscriptionId=" + userSubscription.getId() + "&email=" + userSubscription.getMagazine().getEmail(),OrderStatusInformationDTO.class);
			} 
			catch (RestClientException e) {

				return;
			}
			
			OrderStatus status = OrderStatus.valueOf(response.getBody().getStatus());
			
			if(status != null && !status.equals(userSubscription.getSubscriptionStatus())) {
				userSubscription.setSubscriptionStatus(status);
				save(userSubscription);
			}
		}
	}

}
