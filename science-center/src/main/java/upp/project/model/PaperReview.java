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
public class PaperReview {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Paper paper;
	
	@ManyToOne
	private RegisteredUser reviewer;
	
	@Column
	private String commentforAuthor;
	
	@Column
	private String commentforEditor;
	
	@Enumerated(EnumType.STRING)
	@Column
	private PaperRecommendation recommendation;
	
	public PaperReview() {
		
	}

	public PaperReview(Paper paper, RegisteredUser reviewer, String commentforAuthor, String commentforEditor,
			PaperRecommendation recommendation) {
		this.paper = paper;
		this.reviewer = reviewer;
		this.commentforAuthor = commentforAuthor;
		this.commentforEditor = commentforEditor;
		this.recommendation = recommendation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public RegisteredUser getReviewer() {
		return reviewer;
	}

	public void setReviewer(RegisteredUser reviewer) {
		this.reviewer = reviewer;
	}

	public String getCommentforAuthor() {
		return commentforAuthor;
	}

	public void setCommentforAuthor(String commentforAuthor) {
		this.commentforAuthor = commentforAuthor;
	}

	public String getCommentforEditor() {
		return commentforEditor;
	}

	public void setCommentforEditor(String commentforEditor) {
		this.commentforEditor = commentforEditor;
	}

	public PaperRecommendation getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(PaperRecommendation recommendation) {
		this.recommendation = recommendation;
	}
	
}
