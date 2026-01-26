package com.computerseekho.api.controller;

import com.computerseekho.api.dto.request.EmailRequestDTO;
import com.computerseekho.api.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<String> sendEmail(
            @RequestBody EmailRequestDTO dto
    ) {
        emailService.sendEnquiryEmail(dto);
        return ResponseEntity.ok("Email sent successfully");
    }
}
