package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    @Override
    public String userSave(UserDto userDto) {
        // Email validation
        if (!userDto.getEmail().endsWith("@loyalbridge.io")) {
            throw new IllegalArgumentException("Email must end with @loyalbridge.io");
        }

        // Duplicate email check
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        // Password validation
        String password = userDto.getPassword();
        if (password.length() < 12 ||
                !password.matches(".*[A-Z].*") ||
                !password.matches(".*[!@#$%^&*()_+=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            throw new IllegalArgumentException("Password must be at least 12 characters long, include one uppercase letter and one special character.");
        }

        // Map DTO to entity
        User user = modelMapper.map(userDto, User.class);

        // Encode password
        user.setPassword(passwordEncoder.encode(password));

        // Save user
        userRepository.save(user);

        return "User registered successfully!";
    }
}
