package com.dharm.employee_management_system.service;
import com.dharm.employee_management_system.dto.UpdateEmployeeDTO;
import com.dharm.employee_management_system.dto.UserResponseDTO;
import com.dharm.employee_management_system.dto.LoginRequestDTO;
import com.dharm.employee_management_system.dto.RegisterRequestDTO;
import com.dharm.employee_management_system.exception.DuplicateResourceException;
import com.dharm.employee_management_system.exception.InvalidCredentialsException;
import com.dharm.employee_management_system.exception.ResourceNotFoundException;
import com.dharm.employee_management_system.model.User;
import com.dharm.employee_management_system.repository.UserRepository;
import com.dharm.employee_management_system.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User registerUser(RegisterRequestDTO dto) {

        //Check if email already exists
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already registered");
        }

        //create a new User entity and copy data from the DTO
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // hash before saving
        user.setRole("EMPLOYEE"); // server decides this, not the client

        //save to database
        return userRepository.save(user);
    }
    public String loginUser(LoginRequestDTO dto) {

        //Find the user by email
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        //check if the entered password matches the stored hash
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        //Credentials are correct — generate and return a token
        return jwtUtil.generateToken(user.getEmail());
    }
    public List<UserResponseDTO> getAllEmployees() {

        List<User> users = userRepository.findAll();

        List<UserResponseDTO> result = new ArrayList<>();

        for (User user : users) {
            UserResponseDTO dto = new UserResponseDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            );
            result.add(dto);
        }

        return result;
    }
    public UserResponseDTO getEmployeeById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
    public boolean isSelf(Long id, org.springframework.security.core.Authentication authentication) {
        String email = authentication.getName(); // the email stored as "username" in JWT
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null && user.getId().equals(id);
    }
    public UserResponseDTO updateEmployee(Long id, UpdateEmployeeDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setName(dto.getName());
        User updated = userRepository.save(user);

        return new UserResponseDTO(
                updated.getId(),
                updated.getName(),
                updated.getEmail(),
                updated.getRole()
        );
    }
}