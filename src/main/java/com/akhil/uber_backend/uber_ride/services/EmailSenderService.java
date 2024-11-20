package com.akhil.uber_backend.uber_ride.services;

public interface EmailSenderService {

    void sendEmail(String toEmail, String subject, String body);

    void sendEmail(String[] toEmail, String subject, String body);
}