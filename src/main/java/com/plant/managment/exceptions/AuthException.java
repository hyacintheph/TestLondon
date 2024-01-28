package com.plant.managment.exceptions;


import com.plant.managment.exceptions.enums.AuthExceptionEnum;

public class AuthException extends BaseException{
    public AuthException(int code, String message) {
        super(code, message);
    }

    public AuthException(int code, String message, String... values) {
        super(code, message, values);
    }

    public AuthException(AuthExceptionEnum exceptionEnum){
        super(exceptionEnum.code(), exceptionEnum.message());
    }

    public AuthException(AuthExceptionEnum exceptionEnum, String... values){
        super(exceptionEnum.code(), String.format(exceptionEnum.message(), values));
    }
}
