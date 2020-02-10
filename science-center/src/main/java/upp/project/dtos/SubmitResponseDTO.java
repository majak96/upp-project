package upp.project.dtos;

public class SubmitResponseDTO {

	private String nextTask;
	
	private Boolean valid = true;
	
	private Object redirectLink;
	
	public SubmitResponseDTO() {
		
	}
	
	public SubmitResponseDTO(Object redirectLink) {
		this.redirectLink = redirectLink;
	}
	
	public SubmitResponseDTO(String nextTask){
		this.nextTask = nextTask;
	}
	
	public SubmitResponseDTO(String nextTask, Boolean valid, Object redirectLink){
		this.nextTask = nextTask;
		this.valid = valid;
		this.redirectLink = redirectLink;
	}

	public String getNextTask() {
		return nextTask;
	}

	public void setNextTask(String nextTask) {
		this.nextTask = nextTask;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public Object getRedirectLink() {
		return redirectLink;
	}

	public void setRedirectLink(Object redirectLink) {
		this.redirectLink = redirectLink;
	}

}
