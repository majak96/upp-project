package upp.project.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class PaperOrder extends UserOrder {
	
	@ManyToMany
	private Set<Paper> papers;
	
	public PaperOrder() {
		super();
	}
	
	public PaperOrder(Set<Paper> papers) {
		super();
		this.papers = papers;
	}

	public Set<Paper> getPapers() {
		return papers;
	}

	public void setPapers(Set<Paper> papers) {
		this.papers = papers;
	}
}
