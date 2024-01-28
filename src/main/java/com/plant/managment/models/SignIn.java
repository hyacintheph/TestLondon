package com.plant.managment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignIn {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
