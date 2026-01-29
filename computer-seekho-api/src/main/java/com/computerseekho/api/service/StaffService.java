package com.computerseekho.api.service;

import com.computerseekho.api.dto.response.StaffDTO;
import com.computerseekho.api.entity.Staff;
import com.computerseekho.api.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    // Get all faculty members (teaching staff)
    public List<StaffDTO> getAllFaculty() {
        List<Staff> facultyList = staffRepository.findByStaffRoleAndIsActiveTrue("teaching");
        return facultyList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get all staff members
    public List<StaffDTO> getAllStaff() {
        List<Staff> staffList = staffRepository.findAll();
        return staffList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get staff by ID
    public StaffDTO getStaffById(Integer staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
        return convertToDTO(staff);
    }

    // Convert Staff entity to StaffDTO
    private StaffDTO convertToDTO(Staff staff) {
        StaffDTO dto = new StaffDTO();
        dto.setStaffId(staff.getStaffId());
        dto.setStaffName(staff.getStaffName());
        dto.setPhotoUrl(staff.getPhotoUrl());
        dto.setStaffMobile(staff.getStaffMobile());
        dto.setStaffEmail(staff.getStaffEmail());
        dto.setStaffRole(staff.getStaffRole());
        dto.setStaffBio(staff.getStaffBio());
        dto.setStaffDesignation(staff.getStaffDesignation());
        dto.setIsActive(staff.getIsActive());
        dto.setCreatedAt(staff.getCreatedAt());
        dto.setUpdatedAt(staff.getUpdatedAt());
        return dto;
    }
}
