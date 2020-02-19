package upp.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserSubscription {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "orderStatus")
	@Enumerated(EnumType.STRING)
	private OrderStatus subscriptionStatus;
	
	@ManyToOne
	private RegisteredUser user;
	
	@ManyToOne
	private Magazine magazine;

	public UserSubscription() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserSubscription(OrderStatus subscriptionStatus, Magazine magazine, RegisteredUser user) {
		super();
		this.subscriptionStatus = subscriptionStatus;
		this.user = user;
		this.magazine = magazine;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Magazine getMagazine() {
		return magazine;
	}

	public void setMagazine(Magazine magazine) {
		this.magazine = magazine;
	}

	public OrderStatus getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(OrderStatus subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}
	
}
