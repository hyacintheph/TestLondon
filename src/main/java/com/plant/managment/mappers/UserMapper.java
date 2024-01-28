package com.plant.managment.mappers;


import com.plant.managment.dtos.UserDto;
import com.plant.managment.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User, UserDto>{
    @Override
    @Mapping(target = "email", source = "email")
    UserDto asDTO(User entity);

    @Override
    @Mapping(target = "email", source = "email")
    User asEntity(UserDto dto);
}
