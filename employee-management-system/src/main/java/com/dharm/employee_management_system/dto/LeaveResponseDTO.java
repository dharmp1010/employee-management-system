package com.dharm.employee_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class LeaveResponseDTO {
    private Long id;
    private String reason;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String employeeName;
}