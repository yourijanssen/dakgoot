package com.dakgoot.dakgoot.repository;

import com.dakgoot.dakgoot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}