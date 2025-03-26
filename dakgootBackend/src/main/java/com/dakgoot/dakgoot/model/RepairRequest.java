package com.dakgoot.dakgoot.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RepairRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "house_id", nullable = false)
	private House house;

	@Column(nullable = false)
	private String description;

	@Column
	private String repairType; // e.g., "Gutter", "Roof", "Plumbing", etc.

	@Column
	private String photoUrl;

	@Column
	private String comments;

	@Enumerated(EnumType.STRING)
	private RepairStatus status = RepairStatus.PENDING;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@ManyToOne
	@JoinColumn(name = "created_by_id", nullable = false)
	private User createdBy;


	// Enums for status
	public enum RepairStatus {
		PENDING,
		IN_PROGRESS,
		COMPLETED,
		REJECTED
	}

	// Constructors
	public RepairRequest() {
		this.createdAt = LocalDateTime.now();
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRepairType() {
		return repairType;
	}

	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public RepairStatus getStatus() {
		return status;
	}

	public void setStatus(RepairStatus status) {
		this.status = status;
	}

	public void updateCreatedAt() {
		this.createdAt = LocalDateTime.now();
	}

	// Optional: Method to set created at with a specific time
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
}