//package com.dakgoot.dakgoot.controller;
//
//import com.dakgoot.dakgoot.model.House;
//import com.dakgoot.dakgoot.model.RepairRequest;
//import com.dakgoot.dakgoot.model.User;
//import com.dakgoot.dakgoot.repository.HouseRepository;
//import com.dakgoot.dakgoot.repository.RepairRequestRepository;
//import com.dakgoot.dakgoot.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class HouseAndRepairController {
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private HouseRepository houseRepository;
//
//	@Autowired
//	private RepairRequestRepository repairRequestRepository;
//
//
//	// Add a house for a user
//	@PostMapping("/users/{userId}/houses")
//	public ResponseEntity<House> addHouseToUser(
//			@PathVariable Long userId,
//			@RequestBody House house
//	) {
//		User user = userRepository.findById(userId)
//				.orElseThrow(() -> new RuntimeException("User not found"));
//
//		user.addHouse(house);
//		House savedHouse = houseRepository.save(house);
//		return ResponseEntity.ok(savedHouse);
//	}
//
//	// Get all houses for a user
//	@GetMapping("/users/{userId}/houses")
//	public ResponseEntity<List<House>> getUserHouses(@PathVariable Long userId) {
//		List<House> houses = houseRepository.findByOwnerId(userId);
//		return ResponseEntity.ok(houses);
//	}
//
//	// Create a repair request for a house
//	@PostMapping("/houses/{houseId}/repair-requests")
//	public ResponseEntity<RepairRequest> createRepairRequest(
//			@PathVariable Long houseId,
//			@RequestBody RepairRequest repairRequest,
//			@RequestParam Long userId
//	) {
//		House house = houseRepository.findById(houseId)
//				.orElseThrow(() -> new RuntimeException("House not found"));
//
//		User user = userRepository.findById(userId)
//				.orElseThrow(() -> new RuntimeException("User not found"));
//
//		repairRequest.setHouse(house);
//		repairRequest.setCreatedBy(user);
//
//		RepairRequest savedRepairRequest = repairRequestRepository.save(repairRequest);
//		return ResponseEntity.ok(savedRepairRequest);
//	}
//
//	// Get repair requests for a house
//	@GetMapping("/houses/{houseId}/repair-requests")
//	public ResponseEntity<List<RepairRequest>> getHouseRepairRequests(@PathVariable Long houseId) {
//		List<RepairRequest> repairRequests = repairRequestRepository.findByHouseId(houseId);
//		return ResponseEntity.ok(repairRequests);
//	}
//
//	// Get repair requests created by a user
//	@GetMapping("/users/{userId}/repair-requests")
//	public ResponseEntity<List<RepairRequest>> getUserRepairRequests(@PathVariable Long userId) {
//		List<RepairRequest> repairRequests = repairRequestRepository.findByCreatedById(userId);
//		return ResponseEntity.ok(repairRequests);
//	}
//}