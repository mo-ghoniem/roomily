package com.moghoneim.roomily.property_search.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PropertySearchRequest {
    private String title;
    private String location;

    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    private Long checkIn;   // epoch millis
    private Long checkOut;  // epoch millis
}
