package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.PartnerDto;
import org.example.service.PartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partners")
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;
    @PostMapping
    public ResponseEntity<?> createPartner(@RequestBody PartnerDto dto) {
        try {
            partnerService.savePartner(dto);
            return ResponseEntity.ok("Partner added successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePartner(@PathVariable Long id, @RequestBody PartnerDto dto) {
        try {
            partnerService.updatePartner(id, dto);
            return ResponseEntity.ok("Partner updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePartner(@PathVariable Long id) {
        try {
            partnerService.deletePartner(id);
            return ResponseEntity.ok("Partner deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<PartnerDto>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

}
