package com.dakgoot.dakgoot.modelDTO;

import com.dakgoot.dakgoot.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {
	private Long id;

	@NotBlank(message = "Name is required")
	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

	@Size(max = 20, message = "Phone number cannot exceed 20 characters")
	private String phone;

	private UserRole role;

	private List<HouseDTO> houses;

	// Constructors
	public UserDTO() {}

	public UserDTO(Long id, String name, String email, String phone, UserRole role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public List<HouseDTO> getHouses() {
		return houses;
	}

	public void setHouses(List<HouseDTO> houses) {
		this.houses = houses;
	}

	// Builder pattern for easier object creation
	public static class UserDTOBuilder {
		private Long id;
		private String name;
		private String email;
		private String phone;
		private UserRole role;
		private List<HouseDTO> houses;

		public UserDTOBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public UserDTOBuilder name(String name) {
			this.name = name;
			return this;
		}

		public UserDTOBuilder email(String email) {
			this.email = email;
			return this;
		}

		public UserDTOBuilder phone(String phone) {
			this.phone = phone;
			return this;
		}

		public UserDTOBuilder role(UserRole role) {
			this.role = role;
			return this;
		}

		public UserDTOBuilder houses(List<HouseDTO> houses) {
			this.houses = houses;
			return this;
		}

		public UserDTO build() {
			UserDTO userDTO = new UserDTO(id, name, email, phone, role);
			userDTO.setHouses(houses);
			return userDTO;
		}
	}

	// Equals and HashCode
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDTO userDTO = (UserDTO) o;
		return id != null && id.equals(userDTO.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	// ToString
	@Override
	public String toString() {
		return "UserDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", role=" + role +
				'}';
	}


}