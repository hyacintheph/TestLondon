package com.plant.managment.exceptions.enums;

import com.plant.managment.exceptions.ExceptionEnumBehavior;

public enum PlantExceptionEnum implements ExceptionEnumBehavior {
    PLANT_ALREADY_WATERED(20000,"La plante est déjà arrosée"),
    ERROR_SEND_EMAIL(20001, "Erreur de notification par mail %s")
    ;

    private int code;
    private String message;
    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    PlantExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
