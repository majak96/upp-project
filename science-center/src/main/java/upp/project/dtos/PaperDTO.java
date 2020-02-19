package upp.project.dtos;

import javax.validation.constraints.NotNull;

import upp.project.model.Paper;

public class PaperDTO {
	
	@NotNull
	private Long id;
	
	@NotNull
	private String title;
	
	@NotNull
	private String author;
	
	@NotNull
	private Double price;

	public PaperDTO(Paper paper) {
		this.id = paper.getId();
		this.title = paper.getTitle();
		this.author = paper.getAuthor().getFirstName() + " " + paper.getAuthor().getLastName();
		this.price = paper.getPrice();
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
