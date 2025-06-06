package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerDto {
    private Long id;
    private String name;
    private String apiUrl;
    private String authMethod;
    private double conversionRate;
    private boolean isEnabled;
}
