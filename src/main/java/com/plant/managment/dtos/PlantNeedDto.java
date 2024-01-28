package com.plant.managment.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link com.plant.managment.entities.PlantNeed} entity
 */
@AllArgsConstructor
@NoArgsConstructor
public class PlantNeedDto implements Serializable {
    private  double quantity;
    private  int frequency;

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}