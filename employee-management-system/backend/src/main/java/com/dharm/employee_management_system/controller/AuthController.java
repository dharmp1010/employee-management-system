package com.dharm.employee_management_system.controller;

import com.dharm.employee_management_system.dto.AuthResponseDTO;
import com.dharm.employee_management_system.dto.LoginRequestDTO;
import com.dharm.employee_management_system.dto.RegisterRequestDTO;
import com.dharm.employee_management_system.model.User;
import com.dharm.employee_management_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO dto) {
        User savedUser = userService.registerUser(dto);
        return ResponseEntity.ok("User registered successfully with id: " + savedUser.getId());
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto) {
        String token = userService.loginUser(dto);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}