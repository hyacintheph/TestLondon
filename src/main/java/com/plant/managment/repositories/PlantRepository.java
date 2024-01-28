package com.plant.managment.repositories;

import com.plant.managment.entities.Plant;
import com.plant.managment.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, String> {
    List<Plant> findByUser(User user, Pageable pageable);
}