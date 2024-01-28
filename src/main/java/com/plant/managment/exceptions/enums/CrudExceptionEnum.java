package com.plant.managment.exceptions.enums;


import com.plant.managment.exceptions.ExceptionEnumBehavior;

public enum CrudExceptionEnum implements ExceptionEnumBehavior {
    ENTITY_NOT_FOUND(11000, "Not found %s with %s"),
    ENTITY_ALREADY_EXIST(11001, "Entity %s with key : %s already exist"),
    ENTITY_EMPTY_FIELDS(11002, "Champs requis")    ;

    CrudExceptionEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

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
}
