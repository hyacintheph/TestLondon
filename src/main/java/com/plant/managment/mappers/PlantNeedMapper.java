package com.plant.managment.mappers;

import com.plant.managment.dtos.PlantNeedDto;
import com.plant.managment.entities.PlantNeed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlantNeedMapper extends GenericMapper<PlantNeed, PlantNeedDto>{
    @Override
    PlantNeed asEntity(PlantNeedDto dto);

    @Override
    PlantNeedDto asDTO(PlantNeed entity);
}
