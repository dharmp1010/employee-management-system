package com.dharm.employee_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateEmployeeDTO {

    @NotBlank(message = "Name is required")
    private String name;
}