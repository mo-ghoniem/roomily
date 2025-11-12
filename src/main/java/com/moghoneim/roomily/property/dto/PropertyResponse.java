package com.moghoneim.roomily.property.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class PropertyResponse {

    private Long propertyId;

    @JsonProperty("title")
    private String propertyTitle;

    @JsonProperty("imageLinks")
    private String propertyImageLink;

    @JsonProperty("price")
    private String propertyPrice;

    @JsonProperty("location")
    private String propertyLocation;

    @JsonProperty("geoLocation")
    private Map<String, String> propertyGeoLocation;

    @JsonProperty("amenities")
    private List<String> propertyAmenities;
}
