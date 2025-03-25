package com.dakgoot.dakgoot.controller;

import com.dakgoot.dakgoot.model.UserObj;
import com.dakgoot.dakgoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/auth")
public class AccountController {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Handles user login.
	 *
	 * @param email the user's email
	 * @param password the user's password
	 * @return a message indicating success or failure
	 */
	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password) {
		UserObj user = userRepository.findByEmail(email);
		if (user != null && user.getPassword().equals(password)) {
			return "Login successful!";
		} else {
			return "Invalid email or password.";
		}
	}

	/**
	 * Handles user registration.
	 *
	 * @param user the user to register
	 * @return a message indicating success or failure
	 */
	@PostMapping("/register")
	public String register(@RequestBody UserObj user) {
		if (userRepository.findByEmail(user.getEmail()) != null) {
			return "Email already in use.";
		}
		userRepository.save(user);
		return "Registration successful!";
	}

	/**
	 * Handles user logout.
	 *
	 * @param session the HTTP session
	 * @return a message indicating success
	 */
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "Logout successful!";
	}
}