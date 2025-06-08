package org.example.service;

import org.example.dto.PartnerDto;

import java.util.List;

public interface PartnerService {
    void savePartner(PartnerDto dto);
    void updatePartner(Long id, PartnerDto dto);
    void deletePartner(Long id);
    List<PartnerDto> getAllPartners();
}
