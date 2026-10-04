package com.dharm.employee_management_system.controller;

import com.dharm.employee_management_system.dto.LeaveRequestDTO;
import com.dharm.employee_management_system.dto.LeaveResponseDTO;
import com.dharm.employee_management_system.service.LeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping
    public ResponseEntity<LeaveResponseDTO> applyLeave(
            @Valid @RequestBody LeaveRequestDTO dto,
            Authentication authentication) {

        LeaveResponseDTO leave = leaveService.applyLeave(dto, authentication.getName());
        return ResponseEntity.ok(leave);
    }
    @GetMapping("/my")
    public ResponseEntity<List<LeaveResponseDTO>> getMyLeaves(Authentication authentication) {
        List<LeaveResponseDTO> leaves = leaveService.getMyLeaves(authentication.getName());
        return ResponseEntity.ok(leaves);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LeaveResponseDTO>> getAllLeaves() {
        List<LeaveResponseDTO> leaves = leaveService.getAllLeaves();
        return ResponseEntity.ok(leaves);
    }
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LeaveResponseDTO> approveLeave(@PathVariable Long id) {
        LeaveResponseDTO leave = leaveService.updateLeaveStatus(id, "APPROVED");
        return ResponseEntity.ok(leave);
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LeaveResponseDTO> rejectLeave(@PathVariable Long id) {
        LeaveResponseDTO leave = leaveService.updateLeaveStatus(id, "REJECTED");
        return ResponseEntity.ok(leave);
    }
}