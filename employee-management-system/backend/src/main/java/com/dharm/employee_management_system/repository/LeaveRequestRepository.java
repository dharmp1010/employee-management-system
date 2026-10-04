package com.dharm.employee_management_system.repository;

import com.dharm.employee_management_system.model.LeaveRequest;
import com.dharm.employee_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByUser(User user);
}