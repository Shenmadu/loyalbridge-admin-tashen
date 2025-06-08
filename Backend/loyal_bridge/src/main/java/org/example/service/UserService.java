package org.example.service;

import org.example.dto.UserDto;
import org.example.entity.User;

import java.io.PrintWriter;
import java.util.List;

public interface UserService {
    String userSave(UserDto userDto);
    List<UserDto> searchUsers(String name, String phone, User.Status status);
    void updateStatus(Long userId, String status);
    void updateFlags(Long userId, boolean isHighRisk, boolean isVerified);
    void writeUsersToCsv(PrintWriter writer);
}
