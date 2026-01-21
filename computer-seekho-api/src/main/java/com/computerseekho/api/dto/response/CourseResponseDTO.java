package com.computerseekho.api.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseResponseDTO {

    private Integer courseId;
    private String courseName;
    private Integer courseDuration;
    private BigDecimal courseFees;
    private Boolean courseIsActive;
}
