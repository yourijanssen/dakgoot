package com.dakgoot.dakgoot.modelDTO;

import com.dakgoot.dakgoot.model.RepairRequest;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RepairRequestDTO {
	private Long id;

	@NotBlank(message = "Description is required")
	@Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
	private String description;

	private String repairType;
	private RepairRequest.RepairStatus status;
	private String photoUrl;
	private String comments;
	private LocalDateTime createdAt;

	private Long houseId;
	private String houseAddress;

	private Long createdById;
	private String createdByName;

	// Constructors
	public RepairRequestDTO() {
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public RepairRequest.RepairStatus getStatus() {
		return status;
	}

	public void setStatus(RepairRequest.RepairStatus status) {
		this.status = status;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	// Builder Pattern
	public static class RepairRequestDTOBuilder {
		private final RepairRequestDTO dto = new RepairRequestDTO();

		public RepairRequestDTOBuilder id(Long id) {
			dto.id = id;
			return this;
		}

		public RepairRequestDTOBuilder description(String description) {
			dto.description = description;
			return this;
		}

		public RepairRequestDTOBuilder repairType(String repairType) {
			dto.repairType = repairType;
			return this;
		}

		public RepairRequestDTOBuilder status(RepairRequest.RepairStatus status) {
			dto.status = status;
			return this;
		}

		public RepairRequestDTOBuilder photoUrl(String photoUrl) {
			dto.photoUrl = photoUrl;
			return this;
		}

		public RepairRequestDTOBuilder comments(String comments) {
			dto.comments = comments;
			return this;
		}

		public RepairRequestDTOBuilder createdAt(LocalDateTime createdAt) {
			dto.createdAt = createdAt;
			return this;
		}

		public RepairRequestDTOBuilder houseId(Long houseId) {
			dto.houseId = houseId;
			return this;
		}

		public RepairRequestDTOBuilder houseAddress(String houseAddress) {
			dto.houseAddress = houseAddress;
			return this;
		}

		public RepairRequestDTOBuilder createdById(Long createdById) {
			dto.createdById = createdById;
			return this;
		}

		public RepairRequestDTOBuilder createdByName(String createdByName) {
			dto.createdByName = createdByName;
			return this;
		}

		public RepairRequestDTO build() {
			return dto;
		}
	}

	// Equals, HashCode, and ToString methods
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RepairRequestDTO that = (RepairRequestDTO) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(description, that.description) &&
				Objects.equals(repairType, that.repairType) &&
				status == that.status &&
				Objects.equals(photoUrl, that.photoUrl) &&
				Objects.equals(comments, that.comments) &&
				Objects.equals(createdAt, that.createdAt) &&
				Objects.equals(houseId, that.houseId) &&
				Objects.equals(houseAddress, that.houseAddress) &&
				Objects.equals(createdById, that.createdById) &&
				Objects.equals(createdByName, that.createdByName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, description, repairType, status, photoUrl, comments,
				createdAt, houseId, houseAddress, createdById, createdByName);
	}

	@Override
	public String toString() {
		return "RepairRequestDTO{" +
				"id=" + id +
				", description='" + description + '\'' +
				", repairType='" + repairType + '\'' +
				", status=" + status +
				", photoUrl='" + photoUrl + '\'' +
				", comments='" + comments + '\'' +
				", createdAt=" + createdAt +
				", houseId=" + houseId +
				", houseAddress='" + houseAddress + '\'' +
				", createdById=" + createdById +
				", createdByName='" + createdByName + '\'' +
				'}';
	}
}