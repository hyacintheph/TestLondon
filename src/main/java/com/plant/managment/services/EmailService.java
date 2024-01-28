package com.plant.managment.services;

public interface EmailService {
    void sendEmail(String to, String title, String content);
}
