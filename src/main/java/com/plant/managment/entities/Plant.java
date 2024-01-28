package com.plant.managment.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "plant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plant {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String species;
    private String imagePath;
    @Transient
    private MultipartFile file;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private User user;
    @OneToOne
    private PlantNeed plantNeed;
    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Watering> watering = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate creationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}