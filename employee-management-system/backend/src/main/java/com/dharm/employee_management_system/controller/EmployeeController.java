package com.dharm.employee_management_system.controller;

import com.dharm.employee_management_system.dto.UpdateEmployeeDTO;
import com.dharm.employee_management_system.dto.UserResponseDTO;
import com.dharm.employee_management_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllEmployees() {
        List<UserResponseDTO> employees = userService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isSelf(#id, authentication)")
    public ResponseEntity<UserResponseDTO> getEmployeeById(@PathVariable Long id) {
        UserResponseDTO employee = userService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isSelf(#id, authentication)")
    public ResponseEntity<UserResponseDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeDTO dto) {

        UserResponseDTO updated = userService.updateEmployee(id, dto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {

        userService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

}