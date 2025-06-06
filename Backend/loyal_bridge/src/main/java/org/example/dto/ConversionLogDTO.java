package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionLogDTO {
    private Long id;
    private Long userId;
    private Long partnerId;
    private int pointsConverted;
    private double conversionRate;
    private String conversionDate;
}
