package com.plant.managment.repositories;

import com.plant.managment.entities.PlantNeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantNeedRepository extends JpaRepository<PlantNeed, String> {
}