package com.plant.managment.controllers;

import com.plant.managment.models.ApiResponse;
import com.plant.managment.models.Login;
import com.plant.managment.models.SignIn;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface UserController {
    ResponseEntity<ApiResponse<?>> login(@RequestBody Login login, HttpServletRequest request);
    ResponseEntity<ApiResponse<?>> createUser(@RequestBody SignIn signIn, HttpServletRequest request);
    ResponseEntity<ApiResponse<?>> logout(HttpServletRequest request);
    ResponseEntity<ApiResponse> getUser(HttpServletRequest request);
}
