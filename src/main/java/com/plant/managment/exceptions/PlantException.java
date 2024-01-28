package com.plant.managment.exceptions;

import com.plant.managment.exceptions.enums.CrudExceptionEnum;
import com.plant.managment.exceptions.enums.PlantExceptionEnum;

public class PlantException extends BaseException{
    public PlantException(int code, String message) {
        super(code, message);
    }

    public PlantException(int code, String message, String... values) {
        super(code, message, values);
    }
    public PlantException(PlantExceptionEnum exceptionEnum, String... values){
        super(exceptionEnum.code(), String.format(exceptionEnum.message(), values));
    }
}
