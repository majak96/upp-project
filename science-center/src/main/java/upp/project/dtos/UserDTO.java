package upp.project.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import upp.project.model.RegisteredUser;
import upp.project.model.Role;


public class UserDTO {
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private String city;
	
	@NotNull
	private String country;
	
	@NotNull
	private String title;
	
	@NotNull
	private String email;
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	@NotNull
	private Role role;
	
	private List<Long> scientificAreas = new ArrayList<Long>();
	
	public UserDTO() {
		
	}
	
	public UserDTO(RegisteredUser user) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.city = user.getCity();
		this.country = user.getCountry();
		this.title = user.getTitle();
		this.email = user.getEmail();
		this.username = user.getUsername();
		this.password = null;
		this.role = user.getAuthority().getRole();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Long> getScientificAreas() {
		return scientificAreas;
	}

	public void setScientificAreas(List<Long> scientificAreas) {
		this.scientificAreas = scientificAreas;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
