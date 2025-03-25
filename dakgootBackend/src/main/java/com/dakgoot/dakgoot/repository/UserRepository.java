package com.dakgoot.dakgoot.repository;

import com.dakgoot.dakgoot.model.UserObj;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserObj, Long> {
	UserObj findByEmail(String email);
}