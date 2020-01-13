package upp.project.dtos;

import java.util.ArrayList;
import java.util.List;

public class FormDTO {
	
	private String taskId;
	
	private List<FormFieldDTO> fieldList = new ArrayList<FormFieldDTO>();
	
	public FormDTO() {
		// TODO Auto-generated constructor stub
	}

	public FormDTO(String taskId, List<FormFieldDTO> fieldList) {
		this.taskId = taskId;
		this.fieldList = fieldList;
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

}
