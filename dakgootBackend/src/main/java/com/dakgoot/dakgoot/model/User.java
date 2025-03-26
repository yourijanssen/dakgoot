package com.dakgoot.dakgoot.model;

import com.dakgoot.dakgoot.enums.UserRole;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "userObj", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email")
})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column
	private String phone;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role = UserRole.HOMEOWNER;

	// One-to-Many relationship with Houses
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<House> houses = new ArrayList<>();

	// Constructors
	public User() {
		this.houses = new ArrayList<>();
	}

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = UserRole.HOMEOWNER;
		this.houses = new ArrayList<>();
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

	public List<House> getHouses() {
		return houses;
	}

	public void setHouses(List<House> houses) {
		this.houses = houses;
	}

	// Method to add a house
	public void addHouse(House house) {
		if (this.houses == null) {
			this.houses = new ArrayList<>();
		}
		this.houses.add(house);
		house.setOwner(this);
	}

	// Equals and HashCode
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) &&
				Objects.equals(email, user.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email);
	}

	// ToString method
	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", role=" + role +
				'}';
	}
}