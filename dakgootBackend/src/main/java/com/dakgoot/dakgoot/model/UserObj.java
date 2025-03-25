package com.dakgoot.dakgoot.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email")
})
public class UserObj {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String role = "USER"; // Default role

	// Constructors
	public UserObj() {}

	public UserObj(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = "USER";
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	// Equals and HashCode
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserObj userObj = (UserObj) o;
		return Objects.equals(id, userObj.id) &&
				Objects.equals(email, userObj.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email);
	}

	// toString method
	@Override
	public String toString() {
		return "UserObj{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", role='" + role + '\'' +
				'}';
	}
}