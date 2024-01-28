package com.plant.managment.mappers;

import com.plant.managment.dtos.PlantCreateDto;
import com.plant.managment.entities.Plant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlantCreateMapper extends GenericMapper<Plant, PlantCreateDto> {
    @Override
    @Mapping(source = "name", target = "name")
    Plant asEntity(PlantCreateDto dto);

    @Override
    PlantCreateDto asDTO(Plant entity);
}
