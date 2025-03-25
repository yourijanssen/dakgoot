package com.dakgoot.dakgoot.model;

import jakarta.persistence.*;

/**
 * Represents a resident entity with personal contact information.
 */
@Entity
public class Resident {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String email;
	private String phone;

	/**
	 * Gets the ID of the resident.
	 *
	 * @return the ID of the resident
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the ID of the resident.
	 *
	 * @param id the ID to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the name of the resident.
	 *
	 * @return the name of the resident
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the resident.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the email of the resident.
	 *
	 * @return the email of the resident
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email of the resident.
	 *
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the phone number of the resident.
	 *
	 * @return the phone number of the resident
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone number of the resident.
	 *
	 * @param phone the phone number to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}