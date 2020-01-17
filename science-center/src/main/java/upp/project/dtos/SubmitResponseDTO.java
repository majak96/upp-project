package upp.project.dtos;

public class SubmitResponseDTO {

	private String nextTask;
	
	public SubmitResponseDTO() {
		
	}
	
	public SubmitResponseDTO(String nextTask){
		this.nextTask = nextTask;
	}

	public String getNextTask() {
		return nextTask;
	}

	public void setNextTask(String nextTask) {
		this.nextTask = nextTask;
	}

}
