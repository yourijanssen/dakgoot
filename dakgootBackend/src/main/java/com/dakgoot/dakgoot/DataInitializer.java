//package com.dakgoot.dakgoot;
//
//import com.dakgoot.dakgoot.model.Gutter;
//import com.dakgoot.dakgoot.model.House;
//import com.dakgoot.dakgoot.model.Resident;
//import com.dakgoot.dakgoot.repository.HouseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//	@Autowired
//	private HouseRepository houseRepository;
//
//	@Override
//	public void run(String... args) throws Exception {
//		// Create dummy residents
//		Resident resident1 = new Resident();
//		resident1.setName("John Doe");
//		resident1.setEmail("john@example.com");
//		resident1.setPhone("123-456-7890");
//
//		Resident resident2 = new Resident();
//		resident2.setName("Jane Smith");
//		resident2.setEmail("jane@example.com");
//		resident2.setPhone("098-765-4321");
//
//		// Create dummy gutters
//		Gutter gutter1 = new Gutter();
//		gutter1.setPhotoUrl("http://example.com/photo1.jpg");
//		gutter1.setComments("Needs cleaning");
//		gutter1.setPhotoAdded(true);
//
//		Gutter gutter2 = new Gutter();
//		gutter2.setPhotoUrl("http://example.com/photo2.jpg");
//		gutter2.setComments("In good condition");
//		gutter2.setPhotoAdded(true);
//
//		// Create dummy houses
//		House house1 = new House();
//		house1.setAddress("123 Main St");
//		house1.setResident(resident1);
//		house1.setGutters(Arrays.asList(gutter1));
//
//		House house2 = new House();
//		house2.setAddress("456 Elm St");
//		house2.setResident(resident2);
//		house2.setGutters(Arrays.asList(gutter2));
//
//
//		// Save to repository
//		houseRepository.saveAll(Arrays.asList(house1, house2));
//	}
//}