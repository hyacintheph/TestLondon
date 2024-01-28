package com.plant.managment.controllers;

import com.plant.managment.exceptions.BaseException;
import com.plant.managment.models.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface GenericController {
    @ExceptionHandler({RuntimeException.class})
    default ResponseEntity<ApiResponse> handle(Exception e){
        if(e instanceof BaseException){
            ApiResponse response = new ApiResponse();
            response.setCode(((BaseException) e).getCode());
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(ApiResponse.hydrateException(e));
    }
}
