package com.plant.managment.controllers.impl;

import com.plant.managment.controllers.GenericController;
import com.plant.managment.controllers.PlantController;
import com.plant.managment.dtos.PlantCreateDto;
import com.plant.managment.dtos.PlantNeedDto;
import com.plant.managment.entities.Plant;
import com.plant.managment.entities.PlantNeed;
import com.plant.managment.exceptions.CrudException;
import com.plant.managment.mappers.PlantCreateMapper;
import com.plant.managment.mappers.PlantNeedMapper;
import com.plant.managment.models.ApiResponse;
import com.plant.managment.services.PlantService;
import com.plant.managment.services.UserService;
import com.plant.managment.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/plants")
public class PlantControllerImpl implements PlantController {
    private final PlantService plantService;
    private final PlantCreateMapper plantCreateMapper;
    private final PlantNeedMapper plantNeedMapper;
    private final UserService userService;
    private final Utils utils;
    @Override
    @PostMapping(value = "" , consumes = { "multipart/form-data" })
    public ResponseEntity<ApiResponse<?>> saveNewPlant(HttpServletRequest request, PlantCreateDto plantCreateDto) throws IOException {
        if(!userService.checkSession(utils.extractToken(request))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(null));
        }
        Plant plant = plantCreateMapper.asEntity(plantCreateDto);
        System.out.println(plantCreateDto.getCreationDate());
        plant.setCreationDate(LocalDate.parse(plantCreateDto.getCreationDate()));
        return ResponseEntity.ok(new ApiResponse<>(plantService.saveNew(utils.extractToken(request), plant)));
    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getUserPlants(HttpServletRequest request, int page) {
        if(!userService.checkSession(utils.extractToken(request))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(null));
        }
        return ResponseEntity.ok(new ApiResponse<>(plantService.getUserPlants(utils.extractToken(request), page)));
    }

    @Override
    @PostMapping("/plantNeed")
    public ResponseEntity<ApiResponse<?>> savePlantNeed(HttpServletRequest request, String idPlant, PlantNeedDto plantNeedDto) {
        if(!userService.checkSession(utils.extractToken(request))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(null));
        }
        PlantNeed plantNeed = plantNeedMapper.asEntity(plantNeedDto);
        return ResponseEntity.ok(new ApiResponse<>(plantService.savePlantNeed(idPlant, plantNeed)));
    }

    @Override
    @PutMapping("/watered")
    public ResponseEntity<ApiResponse<?>> wateredPlant(HttpServletRequest request, String idPlant) {
        if(!userService.checkSession(utils.extractToken(request))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(null));
        }
        return ResponseEntity.ok(new ApiResponse<>(plantService.wateredPlant(idPlant)));
    }


}
