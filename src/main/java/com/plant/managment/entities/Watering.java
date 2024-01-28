package com.plant.managment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "watering")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Watering {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private LocalDateTime wateringDate;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}