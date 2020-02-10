package upp.project.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Paper {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String title;
	
	@ManyToOne
	private RegisteredUser author;
	
	@ManyToOne
	private Issue issue;
	
	@Column
	private String keywords;
	
	@Column
	private String paperAbstract;
	
	@Column
	private String DOI;
	
	@ManyToOne
	ScientificArea scientificArea;
	
	@Column
	private String pdf;
	
	@OneToMany
	private Set<CoAuthor> coauthors;
	
	@Column
	private Boolean active;
	
	
	public Paper() {
		
	}
	
	public Paper(String title, String keywords, String paperAbstract) {
		this.title = title;
		this.keywords = keywords;
		this.paperAbstract = paperAbstract;
		this.active = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public RegisteredUser getAuthor() {
		return author;
	}

	public void setAuthor(RegisteredUser author) {
		this.author = author;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getPaperAbstract() {
		return paperAbstract;
	}

	public void setPaperAbstract(String paperAbstract) {
		this.paperAbstract = paperAbstract;
	}

	public ScientificArea getScientificArea() {
		return scientificArea;
	}

	public void setScientificArea(ScientificArea scientificArea) {
		this.scientificArea = scientificArea;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<CoAuthor> getCoauthors() {
		return coauthors;
	}

	public void setCoauthors(Set<CoAuthor> coauthors) {
		this.coauthors = coauthors;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public String getDOI() {
		return DOI;
	}

	public void setDOI(String dOI) {
		DOI = dOI;
	}

}
