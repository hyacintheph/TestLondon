package com.plant.managment.repositories;

import com.plant.managment.entities.Logout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogoutRepository extends JpaRepository<Logout, String> {
    Optional<Logout> findByToken(String s);

}