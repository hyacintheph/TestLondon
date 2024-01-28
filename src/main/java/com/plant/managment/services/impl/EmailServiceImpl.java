package com.plant.managment.services.impl;

import com.plant.managment.exceptions.PlantException;
import com.plant.managment.exceptions.enums.PlantExceptionEnum;
import com.plant.managment.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Configuration
@PropertySource("classpath:application.properties")
public class EmailServiceImpl implements EmailService {
    private final Environment environment;
    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendEmail(String to, String title, String content) {
       try{
           MimeMessage message = javaMailSender.createMimeMessage();
           MimeMessageHelper helper = new MimeMessageHelper(message);
           helper.setFrom(environment.getProperty("spring.mail.username"));
           helper.setTo(to);
           helper.setText(content, true);
           message.setSubject(title);
           javaMailSender.send(message);
       }catch (Exception e){
           throw new PlantException(PlantExceptionEnum.ERROR_SEND_EMAIL, e.getMessage());
       }
    }
}
