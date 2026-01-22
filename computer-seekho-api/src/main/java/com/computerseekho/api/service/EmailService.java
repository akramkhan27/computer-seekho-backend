package com.computerseekho.api.service;

import com.computerseekho.api.dto.request.EmailRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEnquiryEmail(EmailRequestDTO dto) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("sheekhocomputer@gmail.com");   // FIXED RECEIVER
        message.setSubject("New Enquiry Received");

        message.setText(
                "Name : " + dto.getName() + "\n" +
                        "Email: " + dto.getEmail() + "\n\n" +
                        "Message:\n" + dto.getMessage()
        );

        mailSender.send(message);
    }
}
