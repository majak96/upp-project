package upp.project.dtos;

import java.io.Serializable;

public class FormValueDTO implements Serializable{
	
	private String id;
	
	private String value;

	public FormValueDTO() {
		// TODO Auto-generated constructor stub
	}

	public FormValueDTO(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
