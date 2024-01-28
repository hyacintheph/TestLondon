package com.plant.managment.utils;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Service
public class UtilsImpl implements Utils{
    @Override
    public boolean verifyRegex(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(content).matches();
    }



    @Override
    public boolean verifyEmail(String email, String regex) {
        if(email != null){
            return verifyRegex(email, regex);
        }
        return true;
    }

    public String extractToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")){
            return authorizationHeader.substring(7);
        }
        return null;
    }

}
