package upp.project.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OrderInformationDTO {

	@NotNull
	@Email
	private String email;
	
	private Long orderId;

	@NotNull
	@Positive
	private Double paymentAmount;

	@NotNull
	private String paymentCurrency;
	
	
	private String successUrl;
	
	
	private String errorUrl;
	
	
	private String failedUrl;
	
	public OrderInformationDTO() {
		
	}

	public OrderInformationDTO(String email, Double paymentAmount, String paymentCurrency, String successUrl, String errorUrl, String failedUrl, Long id) {
		this.email = email;
		this.paymentAmount = paymentAmount;
		this.paymentCurrency = paymentCurrency;
		this.successUrl = successUrl;
		this.failedUrl = failedUrl;
		this.errorUrl = errorUrl;
		this.orderId = id;
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

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


}
