package upp.project.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import upp.project.model.UserSubscription;

public class SubscriptionInformationDTO {

	@NotNull
	@Email
	private String email;
	
	@NotNull
	private Long subscriptionId;
	
	@NotNull
	@Positive
	private Double paymentAmount;

	@NotNull
	private String paymentCurrency;
	
	@NotNull
	private String successUrl;
	
	@NotNull
	private String errorUrl;
	
	@NotNull
	private String failedUrl;

	public SubscriptionInformationDTO() {
	
	}
	
	public SubscriptionInformationDTO(UserSubscription subscription, String successUrl, String errorUrl, String failedUrl) {
		super();
		this.email = subscription.getMagazine().getEmail();
		this.subscriptionId = subscription.getId();
		this.paymentAmount = subscription.getMagazine().getMonthlyMembershipPrice();
		this.paymentCurrency = "USD";
		this.successUrl = successUrl;
		this.errorUrl = errorUrl;
		this.failedUrl = failedUrl;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

	public String getFailedUrl() {
		return failedUrl;
	}

	public void setFailedUrl(String failedUrl) {
		this.failedUrl = failedUrl;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentCurrency() {
		return paymentCurrency;
	}

	public void setPaymentCurrency(String paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

}
