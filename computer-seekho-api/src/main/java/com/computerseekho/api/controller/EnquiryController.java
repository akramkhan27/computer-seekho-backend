package com.computerseekho.api.controller;

import com.computerseekho.api.dto.request.EnquiryCreateRequestDTO;
import com.computerseekho.api.dto.request.EnquiryFollowUpRequestDTO;
import com.computerseekho.api.dto.response.EnquiryResponseDTO;
import com.computerseekho.api.entity.Enquiry;
import com.computerseekho.api.service.EnquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enquiries")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EnquiryController {

    private final EnquiryService enquiryService;

    // Add Enquiry Page
    @PostMapping
    public Enquiry createEnquiry(@RequestBody EnquiryCreateRequestDTO dto) {
        return enquiryService.createEnquiry(dto);
    }

    // Follow-ups for logged-in staff (TODAY + pending)
    @GetMapping("/followups/staff/{staffId}")
    public List<EnquiryResponseDTO> getUpcomingFollowups(@PathVariable Integer staffId) {
        return enquiryService.getUpcomingFollowupsForStaff(staffId);
    }

    // View All follow-ups (Admin)
    @GetMapping("/followups/all")
    public List<EnquiryResponseDTO> getAllFollowups() {
        return enquiryService.getAllPendingFollowups();
    }

    // CALL button action
    @PutMapping("/followup")
    public Enquiry updateFollowup(@RequestBody EnquiryFollowUpRequestDTO dto) {
        return enquiryService.updateFollowup(dto);
    }
}

