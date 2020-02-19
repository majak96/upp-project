package upp.project.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import upp.project.model.Issue;

public class IssueDTO {
	
	@NotNull
	private Long id;

	@NotNull
	@Positive
	private Integer number;
	
	@NotNull
	@Positive
	private Double price;
	
	public IssueDTO(Issue issue){
		this.id = issue.getId();
		this.number = issue.getNumber();
		this.price = issue.getPrice();
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
