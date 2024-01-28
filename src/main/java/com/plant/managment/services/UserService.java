package com.plant.managment.services;

import com.plant.managment.entities.Logout;
import com.plant.managment.models.Login;
import com.plant.managment.entities.User;
import com.plant.managment.models.SignIn;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public interface UserService extends UserDetailsService {
    String login(Login login);
    User createAccount(SignIn signIn);
    Logout logout(String token);
    User findByEmail(String email);
    String generateToken(Login login, Instant instant, int expirationInstant);
    User decodeToken(String accessToken);
    boolean checkSession(String accessToken);
    User update(User user);

}
