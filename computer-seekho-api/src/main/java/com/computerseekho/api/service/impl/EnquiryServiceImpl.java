package com.computerseekho.api.service.impl;

import com.computerseekho.api.dto.request.EnquiryCreateRequestDTO;
import com.computerseekho.api.dto.request.EnquiryFollowUpRequestDTO;
import com.computerseekho.api.dto.response.EnquiryResponseDTO;
import com.computerseekho.api.entity.*;
import com.computerseekho.api.repository.*;
import com.computerseekho.api.service.EnquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnquiryServiceImpl implements EnquiryService {

    private final EnquiryRepository enquiryRepository;
    private final CourseRepository courseRepository;
    private final StaffRepository staffRepository;
    private final ClosureReasonRepository closureReasonRepository;

    @Override
    public Enquiry createEnquiry(EnquiryCreateRequestDTO dto) {

        Enquiry enquiry = new Enquiry();

        enquiry.setEnquirerName(dto.getEnquirerName());
        enquiry.setStudentName(dto.getStudentName());
        enquiry.setEnquirerAddress(dto.getEnquirerAddress());
        enquiry.setEnquirerMobile(dto.getEnquirerMobile());
        enquiry.setEnquirerAlternateMobile(dto.getEnquirerAlternateMobile());
        enquiry.setEnquirerEmailId(dto.getEnquirerEmailId());
        enquiry.setEnquirerQuery(dto.getEnquirerQuery());
        enquiry.setEnquirySource(dto.getEnquirySource());

        enquiry.setCourse(courseRepository.findById(dto.getCourseId()).orElseThrow());
        enquiry.setStaff(staffRepository.findById(dto.getStaffId()).orElseThrow());

        enquiry.setFollowupDate(
                dto.getFollowupDate() != null
                        ? dto.getFollowupDate()
                        : LocalDate.now().plusDays(3)
        );

        return enquiryRepository.save(enquiry);
    }

    @Override
    public List<EnquiryResponseDTO> getUpcomingFollowupsForStaff(Integer staffId) {

        Staff staff = staffRepository.findById(staffId).orElseThrow();

        return enquiryRepository
                .findByStaffAndIsClosedFalseAndFollowupDateGreaterThanEqual(
                        staff, LocalDate.now()
                )
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnquiryResponseDTO> getAllPendingFollowups() {

        return enquiryRepository
                .findByIsClosedFalseAndFollowupDateGreaterThanEqual(LocalDate.now())
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Enquiry updateFollowup(EnquiryFollowUpRequestDTO dto) {

        Enquiry enquiry = enquiryRepository.findById(dto.getEnquiryId()).orElseThrow();

        enquiry.setSpecialInstructions(dto.getSpecialInstructions());
        enquiry.setInquiryCounter(enquiry.getInquiryCounter() + 1);

        if (dto.getCloseEnquiry() != null && dto.getCloseEnquiry()) {
            enquiry.setIsClosed(true);

            if (dto.getClosureReasonId() != null) {
                enquiry.setClosureReason(
                        closureReasonRepository.findById(dto.getClosureReasonId()).orElseThrow()
                );
            }

            enquiry.setClosureReasonText(dto.getClosureReasonText());
        } else {
            enquiry.setFollowupDate(
                    dto.getNextFollowupDate() != null
                            ? dto.getNextFollowupDate()
                            : LocalDate.now().plusDays(3)
            );
        }

        return enquiryRepository.save(enquiry);
    }

    private EnquiryResponseDTO mapToResponseDTO(Enquiry enquiry) {
        EnquiryResponseDTO dto = new EnquiryResponseDTO();
        dto.setEnquiryId(enquiry.getEnquiryId());
        dto.setEnquirerName(enquiry.getEnquirerName());
        dto.setEnquirerMobile(enquiry.getEnquirerMobile());
        dto.setCourseName(enquiry.getCourse().getCourseName());
        dto.setFollowupDate(enquiry.getFollowupDate());
        dto.setIsClosed(enquiry.getIsClosed());
        return dto;
    }
}
