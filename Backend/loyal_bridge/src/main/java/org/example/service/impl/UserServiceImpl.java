package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<UserDto> searchUsers(String name, String phone, User.Status status) {
        List<User> users = userRepository.findByFilters(name, phone, status);
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public void updateStatus(Long userId, String status) {
        // Handle user not found with meaningful message
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found."));

        // Handle invalid status
        try {
            user.setStatus(User.Status.valueOf(status));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status + ". Allowed values: Freeze, unfreeze.");
        }

        userRepository.save(user);
    }
    @Override
    public void updateFlags(Long userId, boolean isHighRisk, boolean isVerified) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        user.setHighRisk(isHighRisk);
        user.setVerified(isVerified);

        userRepository.save(user);
    }

    @Override
    public void writeUsersToCsv(PrintWriter writer) {
        List<User> users = userRepository.findAll();
        writer.println("ID,Name,Email,Phone,Status");
        for (User user : users) {
            writer.printf("%d,%s,%s,%s,%s%n", user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getStatus());
        }
    }

}
