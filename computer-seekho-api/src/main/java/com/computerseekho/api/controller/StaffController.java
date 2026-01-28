package com.computerseekho.api.controller;

import com.computerseekho.api.dto.response.StaffDTO;
import com.computerseekho.api.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class StaffController {

    @Autowired
    private StaffService staffService;

    // Get all faculty members (teaching staff only)
    @GetMapping("/faculty")
    public ResponseEntity<List<StaffDTO>> getAllFaculty() {
        List<StaffDTO> facultyList = staffService.getAllFaculty();
        return ResponseEntity.ok(facultyList);
    }

    // Get all staff members
    @GetMapping
    public ResponseEntity<List<StaffDTO>> getAllStaff() {
        List<StaffDTO> staffList = staffService.getAllStaff();
        return ResponseEntity.ok(staffList);
    }

    // Get staff by ID
    @GetMapping("/{id}")
    public ResponseEntity<StaffDTO> getStaffById(@PathVariable Integer id) {
        StaffDTO staff = staffService.getStaffById(id);
        return ResponseEntity.ok(staff);
    }
}
