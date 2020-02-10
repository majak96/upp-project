package upp.project.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import upp.project.model.Issue;

public class IssueDTO {

	@NotNull
	@Positive
	private Integer number;
	
	public IssueDTO(Issue issue ){
		this.number = issue.getNumber();
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
}
