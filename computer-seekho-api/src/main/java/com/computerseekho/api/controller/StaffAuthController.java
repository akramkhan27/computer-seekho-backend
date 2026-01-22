package com.computerseekho.api.controller;

import com.computerseekho.api.dto.request.StaffLoginRequest;
import com.computerseekho.api.dto.request.StaffSignupRequest;
import com.computerseekho.api.dto.response.MessageResponse;
import com.computerseekho.api.dto.response.StaffJwtResponse;
import com.computerseekho.api.service.StaffAuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.IOException;

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


    @GetMapping("/oauth2/user")
    public void getOAuth2User(@AuthenticationPrincipal OAuth2User oAuth2User,
                              HttpServletResponse response) throws IOException {

        if (oAuth2User == null) {
            response.sendRedirect("http://localhost:5173/admin/login?error=oauth");
            return;
        }

        String email = oAuth2User.getAttribute("email");

        // Generate JWT using Google email
        StaffJwtResponse jwtResponse = staffAuthService.authenticateStaffWithGoogle(email);

        String token = jwtResponse.getToken();

        // Redirect to React with token
        response.sendRedirect("http://localhost:5173/oauth-success?token=" + token);
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