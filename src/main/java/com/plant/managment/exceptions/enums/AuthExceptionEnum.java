package com.plant.managment.exceptions.enums;


import com.plant.managment.exceptions.ExceptionEnumBehavior;

public enum AuthExceptionEnum implements ExceptionEnumBehavior {
    USER_NOT_FOUND(10001, "Utilisateur non trouvé avec adresse email %s"),
    REQUIRED_INPUTS(10002, "Le nom et le mot de passe sont requis"),
    INVALID_USER(10003,"Adresse email ou mot de passe invalide"),
    INVALID_EMAIL_FORMAT(10004, "Format d'adresse mail invalide"),
    USER_WITH_EMAIL_ALREADY_EXIST(10006, "L'utilisateur avec l'adresse email %s existe déjà !"),
    USER_NOT_FOUND_WITH_EMAIL(10008, "User not found with email %s"),
    USER_NOT_FOUND_WITH_ID(10010, "User not found with id %s"),
    PASSWORD_NOT_MATCH(10013, "Les mots de passes ne correspondent pas !"),
    ;
    private int code;
    private String message;

    AuthExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
