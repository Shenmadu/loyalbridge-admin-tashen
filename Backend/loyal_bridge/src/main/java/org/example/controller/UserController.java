package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.dto.UserFlagDto;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/search")
    public List<UserDto> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) User.Status status
    ) {
        return userService.searchUsers(name, phone, status);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String status = body.get("status");
            userService.updateStatus(id, status);
            return ResponseEntity.ok("User status updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}/flags")
    public ResponseEntity<?> updateFlags(@PathVariable Long id, @RequestBody UserFlagDto dto) {
        try {
            userService.updateFlags(id, dto.isHighRisk(), dto.isVerified());
            return ResponseEntity.ok("Flags updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/export")
    public void exportUsersAsCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=users.csv");
        userService.writeUsersToCsv(response.getWriter());
    }

}
