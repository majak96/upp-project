package upp.project.dtos;

import java.util.List;

import upp.project.model.Issue;
import upp.project.model.Magazine;
import upp.project.model.Paper;
import upp.project.model.UserSubscription;

public class UserPurchasedItemsDTO {

	private List<Magazine> magazines;

	private List<Issue> issues;

	private List<Paper> papers;
	
	private List<UserSubscription> subscriptions;
	
	public UserPurchasedItemsDTO() {
		super();
	}
	
	public UserPurchasedItemsDTO(List<Magazine> magazines, List<Issue> issues, List<Paper> papers,
			List<UserSubscription> subscriptions) {
		super();
		this.magazines = magazines;
		this.issues = issues;
		this.papers = papers;
		this.subscriptions = subscriptions;
	}

	public List<Magazine> getMagazines() {
		return magazines;
	}

	public void setMagazines(List<Magazine> magazines) {
		this.magazines = magazines;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	public List<Paper> getPapers() {
		return papers;
	}

	public void setPapers(List<Paper> papers) {
		this.papers = papers;
	}

	public List<UserSubscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<UserSubscription> subscriptions) {
		this.subscriptions = subscriptions;
	}
}
