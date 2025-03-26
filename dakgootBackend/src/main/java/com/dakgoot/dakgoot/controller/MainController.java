package com.dakgoot.dakgoot.controller;

import com.dakgoot.dakgoot.enums.UserRole;
import com.dakgoot.dakgoot.model.House;
import com.dakgoot.dakgoot.model.LoginRequest;
import com.dakgoot.dakgoot.model.RepairRequest;
import com.dakgoot.dakgoot.model.User;
import com.dakgoot.dakgoot.modelDTO.HouseDTO;
import com.dakgoot.dakgoot.modelDTO.RepairRequestDTO;
import com.dakgoot.dakgoot.modelDTO.UserDTO;
import com.dakgoot.dakgoot.repository.HouseRepository;
import com.dakgoot.dakgoot.repository.RepairRequestRepository;
import com.dakgoot.dakgoot.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class MainController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HouseRepository houseRepository;

	@Autowired
	private RepairRequestRepository repairRequestRepository;

	// Authentication Endpoints
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		User user = userRepository.findByEmail(loginRequest.getEmail());

		if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
			// Create a UserDTO to avoid circular references
			UserDTO userDTO = new UserDTO.UserDTOBuilder()
					.id(user.getId())
					.name(user.getName())
					.email(user.getEmail())
					.phone(user.getPhone())
					.role(user.getRole())
					.houses(
							user.getHouses().stream()
									.map(house -> new HouseDTO.HouseDTOBuilder()
											.id(house.getId())
											.address(house.getAddress())
											.ownerId(user.getId())
											.ownerName(user.getName())
											.build())
									.collect(Collectors.toList())
					)
					.build();

			return ResponseEntity.ok(userDTO);
		} else {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid email or password.");
		}
	}

	@PostMapping("/auth/register")
	public ResponseEntity<?> register(@RequestBody User user) {
		// Check if email already exists
		if (userRepository.findByEmail(user.getEmail()) != null) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Email already in use.");
		}

		try {
			// Validate and set role
			if (user.getRole() == null) {
				user.setRole(UserRole.HOMEOWNER);
			}

			// Save the user
			User savedUser = userRepository.save(user);

			// Remove password before sending
			savedUser.setPassword(null);

			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(savedUser);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Registration failed: " + e.getMessage());
		}
	}

	@PostMapping("/auth/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok("Logout successful!");
	}

	// User Management Endpoints
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<User> users = userRepository.findAll();

		List<UserDTO> userDTOs = users.stream()
				.map(user -> {
					UserDTO dto = new UserDTO();
					dto.setId(user.getId());
					dto.setName(user.getName());
					dto.setEmail(user.getEmail());
					dto.setPhone(user.getPhone());
					dto.setRole(user.getRole());

					// Map houses to DTOs without full user object
					dto.setHouses(user.getHouses().stream()
							.map(house -> {
								HouseDTO houseDTO = new HouseDTO();
								houseDTO.setId(house.getId());
								houseDTO.setAddress(house.getAddress());
								houseDTO.setOwnerId(user.getId());
								return houseDTO;
							})
							.collect(Collectors.toList())
					);

					return dto;
				})
				.collect(Collectors.toList());

		return ResponseEntity.ok(userDTOs);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		try {
			// Check if user exists
			if (!userRepository.existsById(id)) {
				return ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.body(new UserDTO.UserDTOBuilder()
								.id(id)
								.name("User not found")
								.build());
			}

			// Delete the user
			userRepository.deleteById(id);

			// Return success response using UserDTO
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new UserDTO.UserDTOBuilder()
							.id(id)
							.name("User deleted successfully")
							.build());

		} catch (Exception e) {
			// Return error response using UserDTO
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new UserDTO.UserDTOBuilder()
							.id(id)
							.name("Error deleting user: " + e.getMessage())
							.build());
		}
	}

	// House Management Endpoints
	@GetMapping("/users/{userId}/houses")
	public ResponseEntity<List<HouseDTO>> getUserHouses(@PathVariable Long userId) {
		List<House> houses = houseRepository.findByOwnerId(userId);

		List<HouseDTO> houseDTOs = houses.stream()
				.map(house -> {
					// Create UserDTO without circular references
					UserDTO ownerDTO = new UserDTO.UserDTOBuilder()
							.id(house.getOwner().getId())
							.name(house.getOwner().getName())
							.email(house.getOwner().getEmail())
							.phone(house.getOwner().getPhone())
							.role(house.getOwner().getRole())
							.build();

					// Create HouseDTO
					return new HouseDTO.HouseDTOBuilder()
							.id(house.getId())
							.address(house.getAddress())
							.ownerId(house.getOwner().getId())
							.ownerName(house.getOwner().getName())
							.ownerEmail(house.getOwner().getEmail())
							.build();
				})
				.collect(Collectors.toList());

		return ResponseEntity.ok(houseDTOs);
	}

	@PostMapping("/users/{userId}/houses")
	public ResponseEntity<HouseDTO> createHouseForUser(
			@PathVariable Long userId,
			@RequestBody HouseDTO houseDTO
	) {
		try {
			// Find the user
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new RuntimeException("User not found"));

			// Create a new house
			House house = new House();
			house.setAddress(houseDTO.getAddress());
			house.setOwner(user);

			// Save the house
			House savedHouse = houseRepository.save(house);

			// Convert to HouseDTO
			HouseDTO savedHouseDTO = new HouseDTO.HouseDTOBuilder()
					.id(savedHouse.getId())
					.address(savedHouse.getAddress())
					.ownerId(user.getId())
					.ownerName(user.getName())
					.ownerEmail(user.getEmail())
					.build();

			return ResponseEntity.status(HttpStatus.CREATED).body(savedHouseDTO);

		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new HouseDTO.HouseDTOBuilder()
							.address(houseDTO.getAddress())
							.ownerName("Error creating house: " + e.getMessage())
							.build());
		}
	}

	@DeleteMapping("/houses/{houseId}")
	public ResponseEntity<HouseDTO> deleteHouse(
			@PathVariable Long houseId
	) {
		try {
			// Check if house exists
			House house = houseRepository.findById(houseId)
					.orElseThrow(() -> new RuntimeException("House not found"));

			// Optional: Add additional checks
			// For example, check if the current user is the owner
			// You might need to pass the user ID or use authentication context

			// Delete the house
			houseRepository.deleteById(houseId);

			// Create a DTO to return the deleted house details
			HouseDTO deletedHouseDTO = new HouseDTO.HouseDTOBuilder()
					.id(house.getId())
					.address(house.getAddress())
					.ownerId(house.getOwner() != null ? house.getOwner().getId() : null)
					.ownerName(house.getOwner() != null ? house.getOwner().getName() : null)
					.build();

			return ResponseEntity.ok(deletedHouseDTO);

		} catch (Exception e) {
			// Create an error response DTO
			HouseDTO errorDTO = new HouseDTO.HouseDTOBuilder()
					.id(houseId)
					.ownerName("Error deleting house: " + e.getMessage())
					.build();

			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(errorDTO);
		}
	}

	@GetMapping("/houses/management")
	public ResponseEntity<List<HouseDTO>> getHouseManagement() {
		List<House> houses = houseRepository.findAll();

		List<HouseDTO> houseDTOs = houses.stream()
				.map(house -> {
					// Create HouseDTO
					HouseDTO houseDTO = new HouseDTO.HouseDTOBuilder()
							.id(house.getId())
							.address(house.getAddress())
							.ownerId(house.getOwner() != null ? house.getOwner().getId() : null)
							.ownerName(house.getOwner() != null ? house.getOwner().getName() : null)
							.ownerEmail(house.getOwner() != null ? house.getOwner().getEmail() : null)
							.build();

					return houseDTO;
				})
				.collect(Collectors.toList());

		return ResponseEntity.ok(houseDTOs);
	}



	// Repair Request Endpoints

	@GetMapping("/repair-requests")
	public ResponseEntity<List<RepairRequestDTO>> getAllRepairRequests() {
		List<RepairRequest> repairRequests = repairRequestRepository.findAll();

		List<RepairRequestDTO> repairRequestDTOs = repairRequests.stream()
				.map(request -> {
					RepairRequestDTO dto = new RepairRequestDTO.RepairRequestDTOBuilder()
							.id(request.getId())
							.description(request.getDescription())
							.repairType(request.getRepairType())
							.status(request.getStatus())
//							.createdAt(request.getCreatedAt())
							// Add house details
							.houseId(request.getHouse() != null ? request.getHouse().getId() : null)
							.houseAddress(request.getHouse() != null ? request.getHouse().getAddress() : null)
							// Add creator details
							.createdById(request.getCreatedBy() != null ? request.getCreatedBy().getId() : null)
							.createdByName(request.getCreatedBy() != null ? request.getCreatedBy().getName() : null)
							.build();

					return dto;
				})
				.collect(Collectors.toList());

		return ResponseEntity.ok(repairRequestDTOs);
	}

	@GetMapping("/houses/{id}")
	public ResponseEntity<HouseDTO> getHouseDetails(@PathVariable Long id) {
		// Find the house
		House house = houseRepository.findById(id)
				.orElse(null); // Simply return null if house not found

		// Check if house exists
		if (house == null) {
			return ResponseEntity.notFound().build();
		}

		// Create a detailed HouseDTO
		HouseDTO houseDTO = new HouseDTO.HouseDTOBuilder()
				.id(house.getId())
				.address(house.getAddress())
				.ownerId(house.getOwner() != null ? house.getOwner().getId() : null)
				.ownerName(house.getOwner() != null ? house.getOwner().getName() : null)
				.ownerEmail(house.getOwner() != null ? house.getOwner().getEmail() : null)
				.build();

		// Map repair requests
		List<RepairRequestDTO> repairRequestDTOs = house.getRepairRequests() != null ?
				house.getRepairRequests().stream()
						.map(repairRequest -> new RepairRequestDTO.RepairRequestDTOBuilder()
								.id(repairRequest.getId())
								.description(repairRequest.getDescription())
								.repairType(repairRequest.getRepairType())
								.status(repairRequest.getStatus())
//								.createdAt(repairRequest.getCreatedAt())
								.createdById(repairRequest.getCreatedBy() != null ?
										repairRequest.getCreatedBy().getId() : null)
								.createdByName(repairRequest.getCreatedBy() != null ?
										repairRequest.getCreatedBy().getName() : null)
								.build())
						.collect(Collectors.toList())
				: new ArrayList<>();

		// Set repair requests in the DTO
		houseDTO.setRepairRequests(repairRequestDTOs);

		return ResponseEntity.ok(houseDTO);
	}

	@PostMapping("/repair-requests")
	public ResponseEntity<RepairRequestDTO> createRepairRequest(
			@Valid @RequestBody RepairRequestDTO repairRequestDTO,
			@RequestParam Long houseId,
			@RequestParam Long userId
	) {
		try {
			// Find the house
			House house = houseRepository.findById(houseId)
					.orElseThrow(() -> new RuntimeException("House not found"));

			// Find the user
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new RuntimeException("User not found"));

			// Create a new repair request
			RepairRequest repairRequest = new RepairRequest();

			// Set basic details from DTO
			repairRequest.setDescription(repairRequestDTO.getDescription());
			repairRequest.setRepairType(repairRequestDTO.getRepairType());

			// Set default status if not provided
			repairRequest.setStatus(
					repairRequestDTO.getStatus() != null
							? repairRequestDTO.getStatus()
							: RepairRequest.RepairStatus.PENDING
			);

			// Set additional fields
			repairRequest.setPhotoUrl(repairRequestDTO.getPhotoUrl());
			repairRequest.setComments(repairRequestDTO.getComments());

			// Set current timestamp if not provided
//			repairRequest.setCreatedAt(
//					repairRequestDTO.getCreatedAt() != null
//							? repairRequestDTO.getCreatedAt()
//							: LocalDateTime.now()
//			);

			// Set relationships
			repairRequest.setHouse(house);
			repairRequest.setCreatedBy(user);

			// Save the repair request
			RepairRequest savedRepairRequest = repairRequestRepository.save(repairRequest);

			// Convert to DTO for response
			RepairRequestDTO savedRepairRequestDTO = new RepairRequestDTO.RepairRequestDTOBuilder()
					.id(savedRepairRequest.getId())
					.description(savedRepairRequest.getDescription())
					.repairType(savedRepairRequest.getRepairType())
					.status(savedRepairRequest.getStatus())
					.photoUrl(savedRepairRequest.getPhotoUrl())
					.comments(savedRepairRequest.getComments())
//					.createdAt(savedRepairRequest.getCreatedAt())
					.houseId(house.getId())
					.houseAddress(house.getAddress())
					.createdById(user.getId())
					.createdByName(user.getName())
					.build();

			return ResponseEntity.status(HttpStatus.CREATED).body(savedRepairRequestDTO);

		} catch (Exception e) {
			// Log the error
			System.err.println("Error creating repair request: " + e.getMessage());

			// Create an error response DTO
			RepairRequestDTO errorDTO = new RepairRequestDTO.RepairRequestDTOBuilder()
					.description("Error creating repair request: " + e.getMessage())
					.build();

			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(errorDTO);
		}
	}


	@PostMapping("/house/{houseId}/repair-requests")
	public ResponseEntity<RepairRequest> createRepairRequest(
			@PathVariable Long houseId,
			@RequestBody RepairRequest repairRequest,
			@RequestParam Long userId
	) {
		House house = houseRepository.findById(houseId)
				.orElseThrow(() -> new RuntimeException("House not found"));

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		repairRequest.setHouse(house);
		repairRequest.setCreatedBy(user);

		RepairRequest savedRepairRequest = repairRequestRepository.save(repairRequest);
		return ResponseEntity.ok(savedRepairRequest);
	}

	@GetMapping("/house/{houseId}/repair-requests")
	public ResponseEntity<List<RepairRequest>> getHouseRepairRequests(@PathVariable Long houseId) {
		List<RepairRequest> repairRequests = repairRequestRepository.findByHouseId(houseId);
		return ResponseEntity.ok(repairRequests);
	}

	@GetMapping("/users/{userId}/repair-requests")
	public ResponseEntity<List<RepairRequest>> getUserRepairRequests(@PathVariable Long userId) {
		List<RepairRequest> repairRequests = repairRequestRepository.findByCreatedById(userId);
		return ResponseEntity.ok(repairRequests);
	}
}