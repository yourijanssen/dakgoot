//package com.dakgoot.dakgoot.controller;
//
//import java.util.List;
//import java.util.Optional;
//
//import com.dakgoot.dakgoot.enums.UserRole;
//import com.dakgoot.dakgoot.model.User;
//import com.dakgoot.dakgoot.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.servlet.http.HttpSession;
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
//public class AccountController {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	/**
//	 * Handles user login.
//	 *
//	 * @param loginRequest containing email and password
//	 * @return ResponseEntity with user details or error message
//	 */
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//		User user = userRepository.findByEmail(loginRequest.getEmail());
//
//		if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
//			// Remove password before sending user details
//			user.setPassword(null);
//			return ResponseEntity.ok(user);
//		} else {
//			return ResponseEntity
//					.status(HttpStatus.UNAUTHORIZED)
//					.body("Invalid email or password.");
//		}
//	}
//
//	/**
//	 * Handles user registration.
//	 *
//	 * @param user the user to register
//	 * @return ResponseEntity with registered user or error message
//	 */
//	@PostMapping("/register")
//	public ResponseEntity<?> register(@RequestBody User user) {
//		// Check if email already exists
//		if (userRepository.findByEmail(user.getEmail()) != null) {
//			return ResponseEntity
//					.status(HttpStatus.BAD_REQUEST)
//					.body("Email already in use.");
//		}
//
//		try {
//			// Validate and set role
//			if (user.getRole() == null) {
//				user.setRole(UserRole.valueOf("HOMEOWNER"));
//			}
//
//			// Save the user
//			User savedUser = userRepository.save(user);
//
//			// Remove password before sending
//			savedUser.setPassword(null);
//
//			return ResponseEntity
//					.status(HttpStatus.CREATED)
//					.body(savedUser);
//		} catch (Exception e) {
//			return ResponseEntity
//					.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body("Registration failed: " + e.getMessage());
//		}
//	}
//
//	/**
//	 * Handles user logout.
//	 *
//	 * @param session the HTTP session
//	 * @return ResponseEntity with logout message
//	 */
//	@PostMapping("/logout")
//	public ResponseEntity<String> logout(HttpSession session) {
//		session.invalidate();
//		return ResponseEntity.ok("Logout successful!");
//	}
//
//	/**
//	 * Retrieves all users
//	 *
//	 * @return ResponseEntity with list of users
//	 */
//	@GetMapping("/all")
//	public ResponseEntity<List<User>> getAllUsers() {
//		List<User> users = userRepository.findAll();
//		// Optionally remove passwords
//		users.forEach(user -> user.setPassword(null));
//		return ResponseEntity.ok(users);
//	}
//
//	/**
//	 * Deletes a user by their ID
//	 *
//	 * @param id User ID to delete
//	 * @return ResponseEntity with deletion status
//	 */
//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
//		if (!userRepository.existsById(id)) {
//			return ResponseEntity
//					.status(HttpStatus.NOT_FOUND)
//					.body("User not found");
//		}
//
//		try {
//			userRepository.deleteById(id);
//			return ResponseEntity
//					.status(HttpStatus.OK)
//					.body("User deleted successfully");
//		} catch (Exception e) {
//			return ResponseEntity
//					.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body("Error deleting user: " + e.getMessage());
//		}
//	}
//
//
//
//	/**
//	 * Inner class for login request
//	 */
//	public static class LoginRequest {
//		private String email;
//		private String password;
//
//		public String getEmail() {
//			return email;
//		}
//
//		public void setEmail(String email) {
//			this.email = email;
//		}
//
//		public String getPassword() {
//			return password;
//		}
//
//		public void setPassword(String password) {
//			this.password = password;
//		}
//	}
//}