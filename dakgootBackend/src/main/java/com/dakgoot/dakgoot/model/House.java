package com.dakgoot.dakgoot.model;

import jakarta.persistence.*;
import java.util.List;

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

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Resident getResident() {
		return resident;
	}

	public void setResident(Resident resident) {
		this.resident = resident;
	}

	public List<Gutter> getGutters() {
		return gutters;
	}

	public void setGutters(List<Gutter> gutters) {
		this.gutters = gutters;
	}
}