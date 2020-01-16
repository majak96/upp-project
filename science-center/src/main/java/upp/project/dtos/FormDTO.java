package upp.project.dtos;

import java.util.ArrayList;
import java.util.List;

public class FormDTO {
	
	private String taskId;
	
	private String processId;
	
	private String taskName;
	
	private List<FormFieldDTO> fieldList = new ArrayList<FormFieldDTO>();
	
	public FormDTO() {
		// TODO Auto-generated constructor stub
	}

	public FormDTO(String taskId, String processId, String taskName, List<FormFieldDTO> fieldList) {
		this.taskId = taskId;
		this.fieldList = fieldList;
		this.processId = processId;
		this.taskName = taskName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<FormFieldDTO> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<FormFieldDTO> fieldList) {
		this.fieldList = fieldList;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
