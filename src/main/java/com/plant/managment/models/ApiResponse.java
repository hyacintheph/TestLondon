package com.plant.managment.models;

import com.plant.managment.exceptions.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T> {
    private int code;
    private String message;
    private T result;

    public static ApiResponse<Exception> hydrateException(Exception e){
        ApiResponse<Exception> apiResponse = new ApiResponse<>();
        if(e instanceof BaseException){
            apiResponse.code = ((BaseException) e).getCode();
            apiResponse.message = e.getMessage();
        }else{
            apiResponse.code = 5000;
            apiResponse.message = "Internal server error";
            apiResponse.result = e;
        }
        return apiResponse;
    }

    public ApiResponse(T t){
        this.code = 200;
        this.message = "success";
        this.result = t;
    }
}
