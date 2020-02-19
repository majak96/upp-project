package upp.project.dtos;

import java.util.List;

public class UserOrderDTO {

	private String type;
	
	private List<Long> ids;
	
	public UserOrderDTO() {
		
	}

	public UserOrderDTO(String type, List<Long> ids) {
		super();
		this.type = type;
		this.ids = ids;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
}
