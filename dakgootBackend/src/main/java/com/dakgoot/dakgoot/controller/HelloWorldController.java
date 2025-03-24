package com.dakgoot.dakgoot.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dakgoot.dakgoot.model.UserObj;
import com.dakgoot.dakgoot.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello, World!";
	}

	@GetMapping("/getusers")
	public List<UserObj> getUsers() {
		return new ArrayList<>((Collection<? extends UserObj>) userRepository.findAll());
	}

	@PostMapping(path = "/add")
	public @ResponseBody String addNewUser(@RequestParam String name) {
		UserObj user = new UserObj();
		user.setName(name);
		userRepository.save(user);
		return name + " is saved";
	}

	@PutMapping("/update/{id}")
	public String updateUser(@PathVariable Long id, @RequestBody UserObj user) {
		UserObj updatedUser = userRepository.findById(Math.toIntExact(id)).orElse(null);
		if (updatedUser != null) {
			updatedUser.setName(user.getName());
			updatedUser.setEmail(user.getEmail());
			updatedUser.setPassword(user.getPassword());
			userRepository.save(updatedUser);
			return "User id: " + id + " updated";
		} else {
			return "User not found";
		}
	}

	@DeleteMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable Long id) {
		UserObj deleteUser = userRepository.findById(Math.toIntExact(id)).orElse(null);
		if (deleteUser != null) {
			userRepository.delete(deleteUser);
			return "User with id: " + id + " deleted.";
		} else {
			return "User not found";
		}
	}
}