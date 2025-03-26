package com.dakgoot.dakgoot;

import com.dakgoot.dakgoot.enums.UserRole;
import com.dakgoot.dakgoot.model.House;
import com.dakgoot.dakgoot.model.RepairRequest;
import com.dakgoot.dakgoot.model.User;
import com.dakgoot.dakgoot.repository.HouseRepository;
import com.dakgoot.dakgoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Initializes the database with dummy data for users, houses, and repair requests.
 */
@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HouseRepository houseRepository;

	/**
	 * Runs the data initialization process.
	 *
	 * @param args command line arguments
	 * @throws Exception if an error occurs during initialization
	 */
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

			// Create houses for homeowners
			House house1 = createHouse(
					"123 Main St",
					homeowner1,
					createRepairRequest(homeowner1, "Gutter repair needed")
			);

			House house2 = createHouse(
					"456 Elm St",
					homeowner2,
					createRepairRequest(homeowner2, "Roof leak")
			);

			// Save users and houses
			userRepository.saveAll(Arrays.asList(homeowner1, homeowner2, maintenanceUser));
			houseRepository.saveAll(Arrays.asList(house1, house2));
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
			User owner,
			RepairRequest... repairRequests
	) {
		House house = new House();
		house.setAddress(address);
		house.setOwner(owner);

		// Add house to user's houses
		owner.addHouse(house);

		// Add repair requests
		if (repairRequests != null && repairRequests.length > 0) {
			Arrays.stream(repairRequests).forEach(request -> {
				request.setHouse(house);
				house.getRepairRequests().add(request);
			});
		}

		return house;
	}

	/**
	 * Create a repair request
	 */
	private RepairRequest createRepairRequest(User user, String description) {
		RepairRequest request = new RepairRequest();
		request.setDescription(description);
		request.setCreatedBy(user);
		request.setCreatedAt(LocalDateTime.now());
		request.setStatus(RepairRequest.RepairStatus.PENDING);
		request.setRepairType("MAINTENANCE");

		return request;
	}
}