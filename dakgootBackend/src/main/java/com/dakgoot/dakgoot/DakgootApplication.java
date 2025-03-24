package com.dakgoot.dakgoot;

import com.dakgoot.dakgoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DakgootApplication {


	public static void main(String[] args) {
		SpringApplication.run(DakgootApplication.class, args);
	}
}