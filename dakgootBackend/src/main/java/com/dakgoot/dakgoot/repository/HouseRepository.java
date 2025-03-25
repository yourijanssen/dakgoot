package com.dakgoot.dakgoot.repository;

import com.dakgoot.dakgoot.model.House;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
}

