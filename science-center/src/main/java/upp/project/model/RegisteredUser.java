package upp.project.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import upp.project.dtos.UserDTO;

@Entity
public class RegisteredUser {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	@Column
	private String city;
	
	@Column
	private String country;
	
	@Column
	private String title;
	
	@Column
	private String email;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@ManyToOne
	private Authority authority;
	
	@Column
	private boolean confirmed;
	
	@ManyToMany
	private Set<ScientificArea> scientificAreas = new HashSet<>();

	public RegisteredUser() {
		
	}
	
	public RegisteredUser(String firstName, String lastName, String city, String country, String title, String email,
			String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.country = country;
		this.title = title;
		this.email = email;
		this.username = username;
		this.password = password;
		this.confirmed = false;
	}
	
	public RegisteredUser(UserDTO userDTO, Authority authority, List<ScientificArea> scientificAreas) {
		this.firstName = userDTO.getFirstName();
		this.lastName = userDTO.getLastName();
		this.city = userDTO.getCity();
		this.country = userDTO.getCountry();
		this.title = userDTO.getTitle();
		this.email = userDTO.getEmail();
		this.username = userDTO.getUsername();
		this.password = userDTO.getPassword();
		this.confirmed = true;
		this.authority = authority;
		
		for(ScientificArea area : scientificAreas) {
			this.scientificAreas.add(area);
		}
	}
	
	@Override
	public String toString() {
		return id + " | " + firstName + " " + lastName + " | " + username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Set<ScientificArea> getScientificAreas() {
		return scientificAreas;
	}

	public void setScientificAreas(Set<ScientificArea> scientificAreas) {
		this.scientificAreas = scientificAreas;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	
	
}
