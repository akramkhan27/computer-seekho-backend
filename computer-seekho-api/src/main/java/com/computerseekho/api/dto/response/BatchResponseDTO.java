package com.computerseekho.api.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BatchResponseDTO {
    private Integer batchId;
    private String batchName;
    private LocalTime batchStartTime;
    private LocalTime batchEndTime;
    private Integer courseId;
    private String courseName;
    private LocalDateTime presentationDate;
    private BigDecimal courseFees;
    private LocalDate courseFeesFrom;
    private LocalDate courseFeesTo;
    private Boolean batchIsActive;
    private LocalDateTime finalPresentationDate;
    private String batchLogoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}