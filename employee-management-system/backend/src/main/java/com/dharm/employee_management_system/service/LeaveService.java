package com.dharm.employee_management_system.service;

import com.dharm.employee_management_system.dto.LeaveRequestDTO;
import com.dharm.employee_management_system.dto.LeaveResponseDTO;
import com.dharm.employee_management_system.model.LeaveRequest;
import com.dharm.employee_management_system.model.User;
import com.dharm.employee_management_system.repository.LeaveRequestRepository;
import com.dharm.employee_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;

    public LeaveResponseDTO applyLeave(LeaveRequestDTO dto, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LeaveRequest leave = new LeaveRequest();
        leave.setReason(dto.getReason());
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setStatus("PENDING");
        leave.setUser(user);

        LeaveRequest saved = leaveRequestRepository.save(leave);

        return new LeaveResponseDTO(
                saved.getId(),
                saved.getReason(),
                saved.getStartDate(),
                saved.getEndDate(),
                saved.getStatus(),
                user.getName()
        );
    }
    public List<LeaveResponseDTO> getMyLeaves(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<LeaveRequest> leaves = leaveRequestRepository.findByUser(user);
        List<LeaveResponseDTO> result = new ArrayList<>();
        for (LeaveRequest leave : leaves) {
            result.add(new LeaveResponseDTO(
                    leave.getId(),
                    leave.getReason(),
                    leave.getStartDate(),
                    leave.getEndDate(),
                    leave.getStatus(),
                    user.getName()
            ));
        }
        return result;
    }
    public List<LeaveResponseDTO> getAllLeaves() {
        List<LeaveRequest> leaves = leaveRequestRepository.findAll();
        List<LeaveResponseDTO> result = new ArrayList<>();
        for (LeaveRequest leave : leaves) {
            result.add(new LeaveResponseDTO(
                    leave.getId(),
                    leave.getReason(),
                    leave.getStartDate(),
                    leave.getEndDate(),
                    leave.getStatus(),
                    leave.getUser().getName() // notice: leave.getUser(), not our own "user" variable
            ));
        }
        return result;
    }
    public LeaveResponseDTO updateLeaveStatus(Long leaveId, String status) {

        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + leaveId));

        leave.setStatus(status);
        LeaveRequest updated = leaveRequestRepository.save(leave);

        return new LeaveResponseDTO(
                updated.getId(),
                updated.getReason(),
                updated.getStartDate(),
                updated.getEndDate(),
                updated.getStatus(),
                updated.getUser().getName()
        );
    }
}