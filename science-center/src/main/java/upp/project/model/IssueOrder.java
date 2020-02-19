package upp.project.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class IssueOrder extends UserOrder {
	
	@ManyToMany
	private Set<Issue> issue;
	
	public IssueOrder() {
		super();
	}

	public IssueOrder(Set<Issue> issue) {
		super();
		this.issue = issue;
	}

	public Set<Issue> getIssue() {
		return issue;
	}

	public void setIssue(Set<Issue> issue) {
		this.issue = issue;
	}
}
