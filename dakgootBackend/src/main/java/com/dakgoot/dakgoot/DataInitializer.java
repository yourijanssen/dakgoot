package com.dakgoot.dakgoot;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dakgoot.dakgoot.enums.UserRole;
import com.dakgoot.dakgoot.model.House;
import com.dakgoot.dakgoot.model.RepairRequest;
import com.dakgoot.dakgoot.model.User;
import com.dakgoot.dakgoot.repository.HouseRepository;
import com.dakgoot.dakgoot.repository.RepairRequestRepository;
import com.dakgoot.dakgoot.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HouseRepository houseRepository;

	@Autowired
	private RepairRequestRepository repairRequestRepository;

	@Override
	public void run(String... args) throws Exception {
		// Only initialize if no users exist
		if (userRepository.count() == 0) {
			// Create dummy users
			User homeowner1 = createUser(
					"John Doe",
					"john@example.com",
					"password123",
					UserRole.HOMEOWNER,
					"123-456-7890"
			);

			User homeowner2 = createUser(
					"Jane Smith",
					"jane@example.com",
					"password456",
					UserRole.HOMEOWNER,
					"098-765-4321"
			);

			User maintenanceUser = createUser(
					"Maintenance Staff",
					"maintenance@company.com",
					"maintenancepass",
					UserRole.MAINTENANCE,
					"555-123-4567"
			);

			// Save users first to ensure they have IDs
			userRepository.saveAll(Arrays.asList(homeowner1, homeowner2, maintenanceUser));

			// Create houses for homeowners
			House house1 = createHouse(
					"123 Main St",
					homeowner1
			);

			House house2 = createHouse(
					"456 Elm St",
					homeowner2
			);

			// Save houses
			houseRepository.saveAll(Arrays.asList(house1, house2));

			// Create and save repair requests
			RepairRequest request1 = createRepairRequest(
					homeowner1,
					house1,
					"Gutter repair needed",
					"ROOFING"
			);

			RepairRequest request2 = createRepairRequest(
					homeowner2,
					house2,
					"Roof leak in the attic",
					"ROOFING"
			);

			RepairRequest request3 = createRepairRequest(
					homeowner1,
					house1,
					"Electrical outlet not working",
					"ELECTRICAL"
			);

			// Save repair requests
			repairRequestRepository.saveAll(Arrays.asList(request1, request2, request3));
		}
	}

	/**
	 * Create a user with given details
	 */
	private User createUser(
			String name,
			String email,
			String password,
			UserRole role,
			String phone
	) {
		User user = new User(name, email, password);
		user.setRole(role);
		user.setPhone(phone);
		return user;
	}

	/**
	 * Create a house for a user
	 */
	private House createHouse(
			String address,
			User owner
	) {
		House house = new House();
		house.setAddress(address);
		house.setOwner(owner);

		// Add house to user's houses
		owner.addHouse(house);

		return house;
	}

	/**
	 * Create a repair request
	 */
	private RepairRequest createRepairRequest(
			User user,
			House house,
			String description,
			String repairType
	) {
		RepairRequest request = new RepairRequest();
		request.setDescription(description);
		request.setCreatedBy(user);
		request.setHouse(house);
		request.setCreatedAt(LocalDateTime.now());
		request.setStatus(RepairRequest.RepairStatus.PENDING);
		request.setRepairType(repairType);

		// Add repair request to house's repair requests
		house.addRepairRequest(request);

		return request;
	}
}