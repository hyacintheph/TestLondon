package com.plant.managment.exceptions;

public class BaseException extends RuntimeException{
    private int code;
    private String message;

    public BaseException(int code, String message){
        this.code = code;
        this.message = message;
    }

    public BaseException(int code, String message, String... values){
        this.code = code;
        this.message = String.format(message, values);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
