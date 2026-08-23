package com.dharm.employee_management_system.service;

import com.dharm.employee_management_system.dto.RegisterRequestDTO;
import com.dharm.employee_management_system.exception.DuplicateResourceException;
import com.dharm.employee_management_system.model.User;
import com.dharm.employee_management_system.repository.UserRepository;
import com.dharm.employee_management_system.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    UserService userService;

    @Test
    void duplicateEmail_shouldThrowError() {

        // pretend a user already exists with this email
        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(new User()));

        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setEmail("test@example.com");

        // registering with this email should throw an error
        assertThrows(DuplicateResourceException.class, () -> {
            userService.registerUser(dto);
        });
    }
}