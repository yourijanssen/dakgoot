package com.dakgoot.dakgoot.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "house")  // Explicitly name the table
public class House {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String address;

	// Update the relationship annotation
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;

	// One-to-Many relationship with Repair Requests
	@OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RepairRequest> repairRequests = new ArrayList<>();

	// Constructors
	public House() {}

	public House(String address) {
		this.address = address;
	}

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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<RepairRequest> getRepairRequests() {
		return repairRequests;
	}

	public void setRepairRequests(List<RepairRequest> repairRequests) {
		this.repairRequests = repairRequests;
	}

	// Helper method to add a repair request
	public void addRepairRequest(RepairRequest repairRequest) {
		if (repairRequests == null) {
			repairRequests = new ArrayList<>();
		}
		repairRequests.add(repairRequest);
		repairRequest.setHouse(this);
	}
}