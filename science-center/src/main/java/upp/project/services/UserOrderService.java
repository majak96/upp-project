package upp.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import upp.project.dtos.OrderStatusInformationDTO;
import upp.project.model.Issue;
import upp.project.model.IssueOrder;
import upp.project.model.Magazine;
import upp.project.model.MagazineOrder;
import upp.project.model.OrderStatus;
import upp.project.model.Paper;
import upp.project.model.PaperOrder;
import upp.project.model.RegisteredUser;
import upp.project.model.UserOrder;
import upp.project.repositories.IssueOrderRepository;
import upp.project.repositories.MagazineOrderRepository;
import upp.project.repositories.PaperOrderRepository;

@Service
public class UserOrderService {

	@Autowired
	private MagazineOrderRepository magazineRepository;
	
	@Autowired
	private IssueOrderRepository issueRepository;
	
	@Autowired
	private PaperOrderRepository paperRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("https://localhost:8762/api/client/orders/status")
	private String kpUrl;
	
	public UserOrder save(UserOrder order) {
		UserOrder saved = null;
		
		if(order instanceof MagazineOrder) {
			try {
				saved = this.magazineRepository.save((MagazineOrder)order);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(order instanceof IssueOrder) {
			try {
				saved = this.issueRepository.save((IssueOrder)order);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				saved = this.paperRepository.save((PaperOrder) order);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return saved;
	}
	
	public UserOrder getMagazineOrder(Long id) {
		return this.magazineRepository.getOne(id);
	}
	
	public UserOrder getIssueOrder(Long id) {
		return this.issueRepository.getOne(id);
	}
	
	public UserOrder getPaperOrder(Long id) {
		return this.paperRepository.getOne(id);
	}
	
	@Scheduled(initialDelay = 10000, fixedRate = 60000)
	public void checkOrdersStatus() {

		List<UserOrder> orders = new ArrayList<UserOrder>();
		orders.addAll(this.magazineRepository.findOrdersBasedOnOrderStatused(OrderStatus.INITIATED, OrderStatus.CREATED));
		orders.addAll(this.issueRepository.findOrdersBasedOnOrderStatused(OrderStatus.INITIATED, OrderStatus.CREATED));
		orders.addAll(this.paperRepository.findOrdersBasedOnOrderStatused(OrderStatus.INITIATED, OrderStatus.CREATED));
		for (UserOrder order : orders) {
			ResponseEntity<OrderStatusInformationDTO> response = null;
			try {
				response = restTemplate.getForEntity(
						this.kpUrl + "?orderId=" + order.getId() + "&email=" + order.getEmail(),
						OrderStatusInformationDTO.class);
			} catch (RestClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			OrderStatus status = OrderStatus.valueOf(response.getBody().getStatus());
			
			if(status != null && !status.equals(order.getOrderStatus())) {
				order.setOrderStatus(status);
				this.save(order);
			}
		}
	}
	
	public List<Magazine> getAllPurchasedMagazines(RegisteredUser user) {
		return this.magazineRepository.findAllMagazinesFromOrders(user);
	}
	
	public List<Issue> getAllPurchasedIssues(RegisteredUser user) {
		return this.issueRepository.findAllMagazinesFromOrders(user);
	}
		
	public List<Paper> getAllPurchasedPapers(RegisteredUser user) {
		return this.paperRepository.findAllMagazinesFromOrders(user);
	}
}
