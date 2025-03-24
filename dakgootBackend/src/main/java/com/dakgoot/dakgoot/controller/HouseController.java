package com.dakgoot.dakgoot.controller;

import com.dakgoot.dakgoot.model.House;
import com.dakgoot.dakgoot.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/houses")
public class HouseController {

	@Autowired
	private HouseRepository houseRepository;

	@GetMapping
	public List<House> getAllHouses() {
		return houseRepository.findAll();
	}

	@GetMapping("/{id}")
	public House getHouseById(@PathVariable Long id) {
		return houseRepository.findById(id).orElse(null);
	}

	@PostMapping
	public House createHouse(@RequestBody House house) {
		return houseRepository.save(house);
	}

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

	@DeleteMapping("/{id}")
	public void deleteHouse(@PathVariable Long id) {
		houseRepository.deleteById(id);
	}
}