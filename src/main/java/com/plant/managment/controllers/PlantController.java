package com.plant.managment.controllers;

import com.plant.managment.dtos.PlantCreateDto;
import com.plant.managment.dtos.PlantNeedDto;
import com.plant.managment.models.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.IOException;

public interface PlantController {
    ResponseEntity<ApiResponse<?>> saveNewPlant(HttpServletRequest request, @ModelAttribute PlantCreateDto plantCreateDto) throws IOException;
    ResponseEntity<ApiResponse<?>> getUserPlants(HttpServletRequest request, int page);
    ResponseEntity<ApiResponse<?>> savePlantNeed(HttpServletRequest request, @RequestParam String idPlant, @RequestBody PlantNeedDto plantNeedDto);
    ResponseEntity<ApiResponse<?>> wateredPlant(HttpServletRequest request, @RequestParam String idPlant);

}
