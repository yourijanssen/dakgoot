package com.dakgoot.dakgoot.controller;

import com.dakgoot.dakgoot.model.Gutter;
import com.dakgoot.dakgoot.model.House;
import com.dakgoot.dakgoot.model.Resident;
import com.dakgoot.dakgoot.repository.GutterRepository;
import com.dakgoot.dakgoot.repository.HouseRepository;
import com.dakgoot.dakgoot.repository.ResidentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing houses.
 */
@RestController
@RequestMapping("/api/houses")
public class HouseController {

	@Autowired
	private HouseRepository houseRepository;

	@Autowired
	private ResidentRepository residentRepository;

	@Autowired
	private GutterRepository gutterRepository;

	/**
	 * Get all houses.
	 *
	 * @return a list of all houses
	 */
	@GetMapping
	public List<House> getAllHouses() {
		return houseRepository.findAll();
	}

	/**
	 * Get a house by its ID.
	 *
	 * @param id the ID of the house
	 * @return the house with the specified ID, or null if not found
	 */
	@GetMapping("/{id}")
	public House getHouseById(@PathVariable Long id) {
		return houseRepository.findById(id).orElse(null);
	}

	/**
	 * Create a new house.
	 *
	 * @param house the house to create
	 * @return the created house
	 */
	@PostMapping
	public House createHouse(@RequestBody House house) {
		return houseRepository.save(house);
	}

	/**
	 * Update an existing house.
	 *
	 * @param id the ID of the house to update
	 * @param houseDetails the new details of the house
	 * @return the updated house, or null if not found
	 */
	@PutMapping("/{id}")
	public House updateHouse(@PathVariable Long id, @RequestBody House houseDetails) {
		House house = houseRepository.findById(id).orElse(null);
		if (house != null) {
			house.setAddress(houseDetails.getAddress());
			house.setResident(houseDetails.getResident());
			house.setGutters(houseDetails.getGutters());
			return houseRepository.save(house);
		}
		return null;
	}

	/**
	 * Delete a house by its ID, along with its associated resident and gutters.
	 *
	 * @param id the ID of the house to delete
	 */
	@DeleteMapping("/{id}")
	public void deleteHouse(@PathVariable Long id) {
		House house = houseRepository.findById(id).orElse(null);
		if (house != null) {
			houseRepository.delete(house); // Delete the house
		}
		Resident resident = residentRepository.findById(id).orElse(null);
		if (resident != null) {
			residentRepository.delete(resident); // Delete the house
		}
		Gutter gutter = gutterRepository.findById(id).orElse(null);
		if (gutter != null) {
			gutterRepository.delete(gutter); // Delete the house
		}
	}
}