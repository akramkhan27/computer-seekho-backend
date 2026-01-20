package com.computerseekho.api.controller;

import com.computerseekho.api.dto.request.StaffLoginRequest;
import com.computerseekho.api.dto.request.StaffSignupRequest;
import com.computerseekho.api.dto.response.MessageResponse;
import com.computerseekho.api.dto.response.StaffJwtResponse;
import com.computerseekho.api.service.StaffAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class StaffAuthController {

    @Autowired
    private StaffAuthService staffAuthService;

    /**
     * Staff Login endpoint
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateStaff(@Valid @RequestBody StaffLoginRequest loginRequest) {
        StaffJwtResponse jwtResponse = staffAuthService.authenticateStaff(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * Staff Signup/Register endpoint
     * POST /api/auth/signup
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerStaff(@Valid @RequestBody StaffSignupRequest signupRequest) {
        MessageResponse messageResponse = staffAuthService.registerStaff(signupRequest);

        // Check if registration was successful
        if (messageResponse.getMessage().startsWith("Error")) {
            return ResponseEntity.badRequest().body(messageResponse);
        }

        return ResponseEntity.ok(messageResponse);
    }

    /**
     * Test endpoint to check if API is working
     * GET /api/auth/test
     */
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(new MessageResponse("Staff Auth API is working!"));
    }
}