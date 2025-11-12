package com.moghoneim.roomily.property.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyRequest {

    private String propertyTitle;
    private String propertyImageLink;
    private String propertyPrice;
    private String propertyLocation;
    private Map<String, String> propertyGeoLocation;
    private List<String> propertyAmenities;
}
