package com.plant.managment.controllers.impl;

import com.plant.managment.controllers.GenericController;
import com.plant.managment.controllers.UserController;
import com.plant.managment.models.ApiResponse;
import com.plant.managment.models.Login;
import com.plant.managment.models.SignIn;
import com.plant.managment.services.UserService;
import com.plant.managment.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserControllerImpl implements UserController, GenericController {
    private final UserService userService;
    private final Utils utils;

    @Override
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(Login login, HttpServletRequest request) {
        Map<String, String> accessToken = new HashMap<>();
        accessToken.put("access-token", userService.login(login));
        return ResponseEntity.ok(new ApiResponse<>(accessToken));
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createUser(SignIn signIn, HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(userService.createAccount(signIn)));
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(HttpServletRequest request) {
        if(!userService.checkSession(utils.extractToken(request))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(null));
        }
        return ResponseEntity.ok(new ApiResponse<>(userService.logout(utils.extractToken(request))));
    }

    @Override
    @GetMapping("/auth")
    public ResponseEntity<ApiResponse> getUser(HttpServletRequest request) {
        if(!userService.checkSession(utils.extractToken(request))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(null));
        }
        return ResponseEntity.ok(new ApiResponse(userService.decodeToken(utils.extractToken(request))));
    }
}
