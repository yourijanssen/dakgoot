package com.dakgoot.dakgoot.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Represents a house entity with an address, a resident, and multiple gutters.
 */
@Entity
public class House {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String address;

	@OneToOne(cascade = CascadeType.ALL)
	private Resident resident;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Gutter> gutters;

	/**
	 * Gets the ID of the house.
	 *
	 * @return the ID of the house
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the ID of the house.
	 *
	 * @param id the ID to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the address of the house.
	 *
	 * @return the address of the house
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address of the house.
	 *
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the resident of the house.
	 *
	 * @return the resident of the house
	 */
	public Resident getResident() {
		return resident;
	}

	/**
	 * Sets the resident of the house.
	 *
	 * @param resident the resident to set
	 */
	public void setResident(Resident resident) {
		this.resident = resident;
	}

	/**
	 * Gets the list of gutters associated with the house.
	 *
	 * @return the list of gutters
	 */
	public List<Gutter> getGutters() {
		return gutters;
	}

	/**
	 * Sets the list of gutters associated with the house.
	 *
	 * @param gutters the list of gutters to set
	 */
	public void setGutters(List<Gutter> gutters) {
		this.gutters = gutters;
	}
}