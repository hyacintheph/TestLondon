package com.plant.managment.mappers;

import com.plant.managment.dtos.UserCreateDto;
import com.plant.managment.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserCreateMapper extends GenericMapper<User, UserCreateDto>{
    @Override
    @Mapping(source = "email", target = "email")
    UserCreateDto asDTO(User entity);

    @Override
    @Mapping(source = "email", target = "email")
    User asEntity(UserCreateDto dto);
}
