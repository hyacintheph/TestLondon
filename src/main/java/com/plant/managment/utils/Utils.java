package com.plant.managment.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;


@Service
public interface Utils {
    boolean verifyRegex(String content, String regex);

    boolean verifyEmail(String email, String regex);
    String extractToken(HttpServletRequest request);
}
