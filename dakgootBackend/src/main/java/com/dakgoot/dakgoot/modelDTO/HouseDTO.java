package com.dakgoot.dakgoot.modelDTO;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class HouseDTO implements Serializable {
	private Long id;

	@NotBlank(message = "Address is required")
	@Size(min = 3, max = 100, message = "Address must be between 3 and 100 characters")
	private String address;

	private Long ownerId;
	private String ownerName;
	private String ownerEmail;

	private List<RepairRequestDTO> repairRequests;
	private transient long repairRequestCount;

	// Constructors
	public HouseDTO() {}

	public HouseDTO(Long id, String address, Long ownerId, String ownerName, String ownerEmail) {
		this.id = id;
		this.address = address;
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.ownerEmail = ownerEmail;
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

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public List<RepairRequestDTO> getRepairRequests() {
		return repairRequests;
	}

	public void setRepairRequests(List<RepairRequestDTO> repairRequests) {
		this.repairRequests = repairRequests;
	}

	// Builder Pattern
	public static class HouseDTOBuilder {
		private Long id;
		private String address;
		private Long ownerId;
		private String ownerName;
		private String ownerEmail;
		private List<RepairRequestDTO> repairRequests;

		public HouseDTOBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public HouseDTOBuilder address(String address) {
			this.address = address;
			return this;
		}

		public HouseDTOBuilder ownerId(Long ownerId) {
			this.ownerId = ownerId;
			return this;
		}

		public HouseDTOBuilder ownerName(String ownerName) {
			this.ownerName = ownerName;
			return this;
		}

		public HouseDTOBuilder ownerEmail(String ownerEmail) {
			this.ownerEmail = ownerEmail;
			return this;
		}

		public HouseDTOBuilder repairRequests(List<RepairRequestDTO> repairRequests) {
			this.repairRequests = repairRequests;
			return this;
		}

		public HouseDTO build() {
			HouseDTO houseDTO = new HouseDTO(id, address, ownerId, ownerName, ownerEmail);
			houseDTO.setRepairRequests(repairRequests);
			return houseDTO;
		}
	}

	// Equals and HashCode
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		HouseDTO houseDTO = (HouseDTO) o;
		return id != null && id.equals(houseDTO.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	// ToString
	@Override
	public String toString() {
		return "HouseDTO{" +
				"id=" + id +
				", address='" + address + '\'' +
				", ownerId=" + ownerId +
				", ownerName='" + ownerName + '\'' +
				", ownerEmail='" + ownerEmail + '\'' +
				'}';
	}
}