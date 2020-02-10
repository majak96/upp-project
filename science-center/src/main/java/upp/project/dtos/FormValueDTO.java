package upp.project.dtos;

import java.io.Serializable;

public class FormValueDTO implements Serializable{
	
	private String id;
	
	private Object value;

	public FormValueDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public FormValueDTO(String id, Object value) {
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
