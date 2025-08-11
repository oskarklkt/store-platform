package org.example.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvailabilityDto {
    private String uniqId;
    private Integer available;
}
