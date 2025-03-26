package com.dakgoot.dakgoot.repository;

import java.util.List;

import com.dakgoot.dakgoot.model.House;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
	List<House> findByOwnerId(Long ownerId);
}