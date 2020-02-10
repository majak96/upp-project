package upp.project.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Magazine {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String ISSN;
	
	@Enumerated(EnumType.STRING)
	@Column
	private PaymentType paymentType;
	
	@Column
	private Boolean active;
	
	@Column
	private Double monthlyMembershipPrice;
	
	@Column
	private String email;
	
	@ManyToOne
	private RegisteredUser editorInChief;
	
	@ManyToMany
	private Set<RegisteredUser> reviewers = new HashSet<RegisteredUser>();
	
	@ManyToMany
	private Set<RegisteredUser> editors = new HashSet<RegisteredUser>();
	
	@ManyToMany
	private Set<ScientificArea> scientificAreas = new HashSet<>();

	public Magazine() {

	}

	public Magazine(String name, String ISSN, Integer monthlyMembershipPrice) {
		this.name = name;
		this.ISSN = ISSN;
		this.monthlyMembershipPrice = Double.valueOf(monthlyMembershipPrice);
		this.active = false;
		this.email = "mail@gmail.com";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getISSN() {
		return ISSN;
	}

	public void setISSN(String iSSN) {
		ISSN = iSSN;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	
	public RegisteredUser getEditorInChief() {
		return editorInChief;
	}

	public void setEditorInChief(RegisteredUser editorInChief) {
		this.editorInChief = editorInChief;
	}
	
	public Set<ScientificArea> getScientificAreas() {
		return scientificAreas;
	}

	public void setScientificAreas(Set<ScientificArea> scientificAreas) {
		this.scientificAreas = scientificAreas;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<RegisteredUser> getReviewers() {
		return reviewers;
	}

	public void setReviewers(Set<RegisteredUser> reviewers) {
		this.reviewers = reviewers;
	}

	public Set<RegisteredUser> getEditors() {
		return editors;
	}

	public void setEditors(Set<RegisteredUser> editors) {
		this.editors = editors;
	}
	
	@Override
	public String toString() {
		return id + " | " + name;
	}

	public Double getMonthlyMembershipPrice() {
		return monthlyMembershipPrice;
	}

	public void setMonthlyMembershipPrice(Double monthlyMembershipPrice) {
		this.monthlyMembershipPrice = monthlyMembershipPrice;
	}

	public Boolean getActive() {
		return active;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
