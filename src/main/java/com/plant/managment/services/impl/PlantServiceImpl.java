package com.plant.managment.services.impl;

import com.plant.managment.entities.*;
import com.plant.managment.exceptions.CrudException;
import com.plant.managment.exceptions.PlantException;
import com.plant.managment.exceptions.enums.CrudExceptionEnum;
import com.plant.managment.exceptions.enums.PlantExceptionEnum;
import com.plant.managment.repositories.PlantNeedRepository;
import com.plant.managment.repositories.PlantRepository;
import com.plant.managment.repositories.WateringRepository;
import com.plant.managment.services.EmailService;
import com.plant.managment.services.PlantService;
import com.plant.managment.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Configuration
@PropertySource("classpath:application.properties")
public class PlantServiceImpl implements PlantService {
    private final Environment environment;
    private final PlantRepository plantRepository;
    private final UserService userService;
    private final EmailService emailService;
    private final PlantNeedRepository plantNeedRepository;
    private final WateringRepository wateringRepository;
    @Override
    // save new plant
    public Plant saveNew(String token, Plant plant) throws IOException {
        if(plant.getName().isBlank() || plant.getSpecies().isBlank() || plant.getFile() == null){
            throw new CrudException(CrudExceptionEnum.ENTITY_EMPTY_FIELDS);
        }
        // get the image file
        File f = new ClassPathResource("").getFile();
        // set destination path to upload image
        final Path path = Paths.get(f.getAbsolutePath() + File.separator + "static" + File.separator + "images");
        // create this path if it is not exist
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        // replace if this image file already exist on folder
        Path filePath = path.resolve(plant.getFile().getOriginalFilename());
        Files.copy(plant.getFile().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        // get image name and image path
        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/")
                .path(plant.getFile().getOriginalFilename())
                .toUriString();
        // get current user
        User user = userService.decodeToken(token);
        Plant newPlant = new Plant();
        newPlant.setName(plant.getName());
        newPlant.setSpecies(plant.getSpecies());
        newPlant.setStatus(Status.NON_WATERED);
        newPlant.setImagePath(fileUri);
        newPlant.setCreationDate(plant.getCreationDate());
        newPlant.setCreatedAt(LocalDateTime.now());
        newPlant.setUpdatedAt(LocalDateTime.now());
        newPlant.setUser(user);
        // save new plant
        Plant savedPlant =  plantRepository.save(newPlant);
        List<Plant> plants = (List<Plant>) user.getPlants();
        plants.add(savedPlant);
        user.setPlants(plants);
        // update plant to user
        userService.update(user);
        return savedPlant;
    }

    @Override
    // get paginate plants by auth user
    public List<Plant> getUserPlants(String token, int page) {
        User user = userService.decodeToken(token);
        return plantRepository.findByUser(user, PageRequest.of(page, Integer.parseInt(environment.getProperty("app.page.size")),
                Sort.by("createdAt").descending())).stream().toList();
    }

    @Override
    // save new need of plant
    public Plant savePlantNeed(String idPlant, PlantNeed plantNeed) {
        Plant plant = findById(idPlant);
        PlantNeed newPlantNeed = new PlantNeed();
        newPlantNeed.setQuantity(plantNeed.getQuantity());
        newPlantNeed.setFrequency(plantNeed.getFrequency());
        newPlantNeed.setCreatedAt(ZonedDateTime.now(ZoneId.of(environment.getProperty("app.zone.id"))));
        newPlantNeed.setUpdatedAt(ZonedDateTime.now(ZoneId.of(environment.getProperty("app.zone.id"))));
        PlantNeed savedPlantNeed = plantNeedRepository.save(newPlantNeed);
        plant.setPlantNeed(savedPlantNeed);
        return plantRepository.save(plant);
    }

    @Override
    // save new watered plant
    public Plant wateredPlant(String idPlant) {
        Plant plant = findById(idPlant);
        if(plant.getStatus().equals(Status.WATERED)){
            throw new PlantException(PlantExceptionEnum.PLANT_ALREADY_WATERED);
        }
        Watering watering = new Watering();
        watering.setWateringDate(LocalDateTime.now());
        Watering savedWatering = wateringRepository.save(watering);
        List<Watering> wateringList = (List<Watering>) plant.getWatering();
        wateringList.add(savedWatering);
        plant.setWatering(wateringList);
        plant.setStatus(Status.WATERED);
        return plantRepository.save(plant);
    }

    Plant findById(String idPlant){
        Optional<Plant> plantOptional = plantRepository.findById(idPlant);
        if(plantOptional.isEmpty()){
            throw new CrudException(CrudExceptionEnum.ENTITY_NOT_FOUND, idPlant);
        }
        return plantOptional.get();
    }
    // Job of 20s to check all plant without watering
    @Scheduled(fixedDelay = 20000)
    public void verifyUnWateredPlants(){
        // get all plants
       List<Plant> plants = plantRepository.findAll();
       // for each plant, check if it is not watered
       plants.forEach(plant -> {
           // get watering of each plant and sort it
           List<Watering> waterings = plant.getWatering().stream().sorted((date1, date2) -> date1.getWateringDate().isAfter(date2.getWateringDate()) ? 1 : -1).toList();
           if(!waterings.isEmpty()){
               // get last watering date
               LocalDateTime lastWatering = waterings.get(waterings.size() - 1).getWateringDate();
               int frequency = plant.getPlantNeed().getFrequency();
               // calculate the need time to water
               int wateringHours = 86400/frequency;
               // get local time of now
               LocalDateTime now = LocalDateTime.now();
               // check if it is less than the difference of the date of last watering and now
               if(Duration.between(lastWatering, now).toSeconds() > wateringHours){
                   // send email notification to auth user email
                   emailService.sendEmail(plant.getUser().getEmail(), "Arrosage de plante" ,"Veuillez arroser la plant : "+plant.getName());
                   // set the plant not watered
                   if(plant.getStatus().equals(Status.WATERED)){
                       plant.setStatus(Status.NON_WATERED);
                       plantRepository.save(plant);
                   }
               }
           }else{
               // if the plant is not watered yet
               emailService.sendEmail("hyacinthetsague47@gmail.com", "Arrosage de plante" ,"Veuillez arroser la plant : "+plant.getName());
               if(plant.getStatus().equals(Status.WATERED)){
                   plant.setStatus(Status.NON_WATERED);
                   plantRepository.save(plant);
               }
           }
       });
    }
}
