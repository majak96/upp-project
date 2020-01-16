package upp.project.dtos;

public class TaskDTO {
	
	private String id;
	
	private String title;
	
	public TaskDTO(){
		
	}

	public TaskDTO(String id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
