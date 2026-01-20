package com.computerseekho.api.service;

import com.computerseekho.api.dto.request.StaffLoginRequest;
import com.computerseekho.api.dto.request.StaffSignupRequest;
import com.computerseekho.api.dto.response.MessageResponse;
import com.computerseekho.api.dto.response.StaffJwtResponse;
import com.computerseekho.api.entity.Staff;
import com.computerseekho.api.repository.StaffRepository;
import com.computerseekho.api.security.jwt.JwtUtils;
import com.computerseekho.api.security.services.StaffDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StaffAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    // Login staff
    public StaffJwtResponse authenticateStaff(StaffLoginRequest loginRequest) {
        // Authenticate staff
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getStaffUsername(),
                        loginRequest.getStaffPassword()
                )
        );

        // Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Get staff details
        StaffDetailsImpl staffDetails = (StaffDetailsImpl) authentication.getPrincipal();

        // Return JWT response
        return new StaffJwtResponse(
                jwt,
                staffDetails.getStaffId(),
                staffDetails.getStaffUsername(),
                staffDetails.getStaffEmail(),
                staffDetails.getStaffName(),
                staffDetails.getStaffRole()
        );
    }

    // Register new staff
    public MessageResponse registerStaff(StaffSignupRequest signupRequest) {
        // Check if username already exists
        if (staffRepository.existsByStaffUsername(signupRequest.getStaffUsername())) {
            return new MessageResponse("Error: Username is already taken!");
        }

        // Check if email already exists
        if (staffRepository.existsByStaffEmail(signupRequest.getStaffEmail())) {
            return new MessageResponse("Error: Email is already in use!");
        }

        // Validate staff role
        String role = signupRequest.getStaffRole().toLowerCase();
        if (!role.equals("teaching") && !role.equals("non-teaching")) {
            return new MessageResponse("Error: Invalid staff role! Use 'teaching' or 'non-teaching'");
        }

        // Create new staff account
        Staff staff = new Staff(
                signupRequest.getStaffUsername(),
                signupRequest.getStaffEmail(),
                passwordEncoder.encode(signupRequest.getStaffPassword())  // Encrypt password
        );

        // Set additional fields
        staff.setStaffName(signupRequest.getStaffName());
        staff.setPhotoUrl(signupRequest.getPhotoUrl());
        staff.setStaffMobile(signupRequest.getStaffMobile());
        staff.setStaffRole(role);
        staff.setStaffBio(signupRequest.getStaffBio());
        staff.setStaffDesignation(signupRequest.getStaffDesignation());

        staffRepository.save(staff);

        return new MessageResponse("Staff registered successfully!");
    }
}