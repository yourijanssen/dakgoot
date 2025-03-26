//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ModelMapperConfig {
//	@Bean
//	public ModelMapper modelMapper() {
//		ModelMapper mapper = new ModelMapper();
//
//		// Custom mapping configurations
//		mapper.typeMap(User.class, UserDTO.class).addMappings(mapping -> {
//			mapping.map(src -> src.getHouses().size(),
//					(dest, v) -> dest.setHouses(null));
//		});
//
//		mapper.typeMap(House.class, HouseDTO.class).addMappings(mapping -> {
//			mapping.map(src -> src.getOwner().getId(),
//					(dest, v) -> dest.setOwnerId((Long) v));
//			mapping.map(src -> src.getOwner().getName(),
//					(dest, v) -> dest.setOwnerName((String) v));
//		});
//
//		return mapper;
//	}
//}