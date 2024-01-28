package com.plant.managment.repositories;

import com.plant.managment.entities.Watering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WateringRepository extends JpaRepository<Watering, String> {
}