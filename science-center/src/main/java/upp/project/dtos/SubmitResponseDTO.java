package upp.project.dtos;

public class SubmitResponseDTO {

	private String nextTask;
	
	private Boolean valid = true;
	
	public SubmitResponseDTO() {
		
	}
	
	public SubmitResponseDTO(String nextTask){
		this.nextTask = nextTask;
	}
	
	public SubmitResponseDTO(String nextTask, Boolean valid){
		this.nextTask = nextTask;
		this.valid = valid;
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

}
