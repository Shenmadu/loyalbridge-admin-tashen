package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.PartnerDto;
import org.example.entity.Partner;
import org.example.repository.PartnerRepository;
import org.example.service.PartnerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {
    private final PartnerRepository partnerRepository;
    private final ModelMapper modelMapper;
    @Override
    public void savePartner(PartnerDto dto) {
        Partner partner = modelMapper.map(dto, Partner.class);
        partner.setAuthMethod(Partner.AuthMethod.valueOf(dto.getAuthMethod()));
        partnerRepository.save(partner);
    }

    @Override
    public void updatePartner(Long id, PartnerDto dto) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Partner not found"));
        modelMapper.map(dto, partner);
        partner.setAuthMethod(Partner.AuthMethod.valueOf(dto.getAuthMethod()));
        partnerRepository.save(partner);
    }

    @Override
    public void deletePartner(Long id) {
        if (!partnerRepository.existsById(id)) {
            throw new NoSuchElementException("Partner not found");
        }
        partnerRepository.deleteById(id);
    }

    @Override
    public List<PartnerDto> getAllPartners() {
        return partnerRepository.findAll().stream()
                .map(partner -> modelMapper.map(partner, PartnerDto.class))
                .collect(Collectors.toList());
    }
}
