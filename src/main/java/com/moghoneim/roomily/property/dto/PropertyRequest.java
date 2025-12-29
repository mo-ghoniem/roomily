package com.moghoneim.roomily.property.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyRequest {

    private String propertyTitle;
    private String propertyImageLink;
    private BigDecimal propertyPrice;
    private String propertyLocation;
    private Map<String, String> propertyGeoLocation;
    private List<String> propertyAmenities;
    private Long checkInDate;   // epoch millis
    private Long checkOutDate;  // epoch millis
    private Long userId;
}
