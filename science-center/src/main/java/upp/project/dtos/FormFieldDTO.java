package upp.project.dtos;

import java.util.LinkedHashMap;
import java.util.Map;

public class FormFieldDTO {
	
	private String id;
	
	private String label;
	
	private String type;
	
	private Boolean required;
	
	private Boolean email;
	
	private Boolean password;
	
	private Map<String, String> values = new LinkedHashMap<String,String>();

	public FormFieldDTO() {
		this.email = false;
		this.password = false;
	}

	public FormFieldDTO(String id, String label, String type) {
		this.id = id;
		this.label = label;
		this.type = type;
		this.email = false;
		this.password = false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	public Boolean getEmail() {
		return email;
	}

	public void setEmail(Boolean email) {
		this.email = email;
	}

	public Boolean getPassword() {
		return password;
	}

	public void setPassword(Boolean password) {
		this.password = password;
	}

}
