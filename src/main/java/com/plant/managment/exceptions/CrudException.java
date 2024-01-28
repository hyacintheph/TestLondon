package com.plant.managment.exceptions;


import com.plant.managment.exceptions.enums.CrudExceptionEnum;

public class CrudException extends BaseException {
    public CrudException(int code, String message) {
        super(code, message);
    }

    public CrudException(int code, String message, String... values) {
        super(code, message, values);
    }
    public CrudException(CrudExceptionEnum exceptionEnum, String... values){
        super(exceptionEnum.code(), String.format(exceptionEnum.message(), values));
    }
}
