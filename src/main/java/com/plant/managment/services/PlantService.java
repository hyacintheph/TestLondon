package com.plant.managment.services;

import com.plant.managment.entities.Plant;
import com.plant.managment.entities.PlantNeed;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface PlantService  {
    Plant saveNew(String token, Plant plant) throws IOException;
    List<Plant> getUserPlants(String token, int page);
    Plant savePlantNeed(String idPlant, PlantNeed plantNeed);
    Plant wateredPlant(String idPlant);

}
