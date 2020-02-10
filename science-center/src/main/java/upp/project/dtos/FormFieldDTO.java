package upp.project.dtos;

import java.util.LinkedHashMap;
import java.util.Map;

public class FormFieldDTO {
	
	private String id;
	
	private String label;
	
	private String type;
	
	private Boolean required;
	
	private Boolean email;
	
	private Boolean multiple;
	
	private Boolean password;
	
	private Boolean readonly;
	
	private Boolean textarea;
	
	private Boolean upload;
	
	private Boolean download;
	
	private Integer minNumber;
	
	private Object value;
	
	private Map<String, String> values = new LinkedHashMap<String,String>();

	public FormFieldDTO() {
		this.email = false;
		this.password = false;
		this.multiple = false;
		this.readonly = false;
		this.textarea = false;
		this.upload = false;
		this.download = false;
	}

	public FormFieldDTO(String id, String label, String type) {
		this.id = id;
		this.label = label;
		this.type = type;
		this.email = false;
		this.password = false;
		this.multiple = false;
		this.readonly = false;
		this.textarea = false;
		this.upload = false;
		this.download = false;
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

	public Boolean getMultiple() {
		return multiple;
	}

	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

	public Boolean getReadonly() {
		return readonly;
	}

	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Integer getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(Integer minNumber) {
		this.minNumber = minNumber;
	}

	public Boolean getTextarea() {
		return textarea;
	}

	public void setTextarea(Boolean textarea) {
		this.textarea = textarea;
	}

	public Boolean getUpload() {
		return upload;
	}

	public void setUpload(Boolean upload) {
		this.upload = upload;
	}

	public Boolean getDownload() {
		return download;
	}

	public void setDownload(Boolean download) {
		this.download = download;
	}

}
