package com.plant.managment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "logout_token")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Logout {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 5000)
    private String token;
    private ZonedDateTime loggedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}