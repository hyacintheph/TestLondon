package com.plant.managment.services.impl;

import com.plant.managment.entities.Logout;
import com.plant.managment.models.Login;
import com.plant.managment.entities.User;
import com.plant.managment.exceptions.AuthException;
import com.plant.managment.exceptions.enums.AuthExceptionEnum;
import com.plant.managment.models.SignIn;
import com.plant.managment.repositories.LogoutRepository;
import com.plant.managment.repositories.UserRepository;
import com.plant.managment.security.impl.Tokenization;
import com.plant.managment.services.UserService;
import com.plant.managment.utils.Utils;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Configuration
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Resource
    private final Environment environment;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;
    private final LogoutRepository logoutRepository;
    private final Utils utils;
    private final Tokenization tokenization;

    @Override
    public String login(Login login) {
        Instant instant = Instant.now();
        return generateToken(login, instant, Integer.parseInt(environment.getProperty("app.accesstoken.expiration.date")));
    }

    @Override
    // create new user account
    public User createAccount(SignIn signIn) {
        // email validation
        if(!utils.verifyEmail(signIn.getEmail(), environment.getProperty("app.email.regex"))){
            throw new AuthException(AuthExceptionEnum.INVALID_EMAIL_FORMAT);
        }
        // check empty fields
        if(signIn.getName().isBlank() || signIn.getPassword().isBlank()){
            throw new AuthException(AuthExceptionEnum.REQUIRED_INPUTS);
        }
        // check password matching
        if(!signIn.getPassword().equals(signIn.getConfirmPassword())){
            throw new AuthException(AuthExceptionEnum.PASSWORD_NOT_MATCH);
        }
        if(userRepository.existsByEmail(signIn.getEmail())){
            throw new AuthException(AuthExceptionEnum.USER_WITH_EMAIL_ALREADY_EXIST, signIn.getEmail());
        }
        // encrypt password with PasswordEncoder
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setName(signIn.getName());
        user.setEmail(signIn.getEmail());
        user.setRole("SCOPE_USER");
        user.setPassword(passwordEncoder.encode(signIn.getPassword()));
        user.setCreatedAt(ZonedDateTime.now(ZoneId.of(environment.getProperty("app.zone.id"))));
        user.setUpdatedAt(ZonedDateTime.now(ZoneId.of(environment.getProperty("app.zone.id"))));
        return userRepository.save(user);
    }

    @Override
    // logout user
    public Logout logout(String accessToken) {
        Logout logout = new Logout();
        logout.setToken(accessToken);
        logout.setLoggedAt(ZonedDateTime.now(ZoneId.of(environment.getProperty("app.zone.id"))));
        return logoutRepository.save(logout);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new AuthException(AuthExceptionEnum.INVALID_USER);
        }
        return user.get();
    }

    @Override
    // generate new JWT token
    public String generateToken(Login login, Instant instant, int expirationInstant) {
        // get encoder for password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = findByEmail(login.getEmail());
        if(!passwordEncoder.matches(login.getPassword(), user.getPassword())){
            throw new AuthException(AuthExceptionEnum.INVALID_USER);
        }
        // set the JWT data
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(user.getEmail())
                .issuer(environment.getProperty("spring.application.name"))
                .issuedAt(instant)
                .expiresAt(instant.plus(expirationInstant, ChronoUnit.HOURS))
                .claim("scope",user.getRole())
                .build();
        return  jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    @Override
    // get the user by JWT
    public User decodeToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        String email = jwt.getSubject();
        return findByEmail(email);
    }

    @Override
    // get current auth user
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username);
    }

    @Override
    // check if user is currently authenticated
    public boolean checkSession(String accessToken) {
        Optional<Logout> logout = logoutRepository.findByToken(accessToken);
        return logout.isEmpty();
    }

    @Override
    // update user data
    public User update(User user) {
        User savedUser = findByEmail(user.getEmail());
        savedUser.setRole(user.getRole());
        savedUser.setPlants(user.getPlants());
        savedUser.setName(user.getName());
        savedUser.setUpdatedAt(ZonedDateTime.now(ZoneId.of(environment.getProperty("app.zone.id"))));
        return userRepository.save(savedUser);
    }
}
