package com.moghoneim.roomily.property.dto.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moghoneim.roomily.property.dto.PropertyRequest;
import com.moghoneim.roomily.property.dto.PropertyResponse;
import com.moghoneim.roomily.property.PropertyEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PropertyMapper {

    // Method to map PropertyRequest DTO to PropertyInfo Entity
    public PropertyEntity toEntity(PropertyRequest propertyRequest) {
        String geoLocation;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            geoLocation = objectMapper.writeValueAsString(propertyRequest.getPropertyGeoLocation());
        } catch (JsonProcessingException e) {
            geoLocation = "-";
        }

        return PropertyEntity.builder()
                .propertyTitle(propertyRequest.getPropertyTitle())
                .propertyImageLink(propertyRequest.getPropertyImageLink())
                .propertyPrice(propertyRequest.getPropertyPrice())
                .propertyLocation(propertyRequest.getPropertyLocation())
                .propertyGeoLocation(geoLocation)
                .propertyAmenities(propertyRequest.getPropertyAmenities())
                .build();

    }

    // Method to map PropertyInfo Entity to PropertyResponse DTO
    public PropertyResponse toResponse(PropertyEntity propertyInfo) {

        Map<String, String> geoLocation;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            geoLocation = objectMapper.readValue(propertyInfo.getPropertyGeoLocation(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            geoLocation = Map.of();
        }


        return PropertyResponse.builder()
                .propertyId(propertyInfo.getId())
                .propertyTitle(propertyInfo.getPropertyTitle())
                .propertyImageLink(propertyInfo.getPropertyImageLink())
                .propertyPrice(propertyInfo.getPropertyPrice())
                .propertyLocation(propertyInfo.getPropertyLocation())
                .propertyGeoLocation(geoLocation)
                .propertyAmenities(propertyInfo.getPropertyAmenities())
                .build();

    }

    // Method to map PropertyResponse DTO back to PropertyInfo Entity
    public PropertyEntity fromResponse(PropertyResponse propertyResponse) {

        String geoLocation;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            geoLocation = objectMapper.writeValueAsString(propertyResponse.getPropertyGeoLocation());
        } catch (JsonProcessingException e) {
            geoLocation = "-";
        }

        return PropertyEntity.builder()
                .id(propertyResponse.getPropertyId())
                .propertyTitle(propertyResponse.getPropertyTitle())
                .propertyImageLink(propertyResponse.getPropertyImageLink())
                .propertyPrice(propertyResponse.getPropertyPrice())
                .propertyLocation(propertyResponse.getPropertyLocation())
                .propertyGeoLocation(geoLocation)
                .propertyAmenities(propertyResponse.getPropertyAmenities())
                .build();
    }


    // Method to update an existing PropertyInfo entity from a PropertyRequest DTO
    public void updateEntityFromRequest(PropertyRequest propertyRequest, PropertyEntity propertyInfo) {
        if (propertyRequest.getPropertyTitle() != null) {
            propertyInfo.setPropertyTitle(propertyRequest.getPropertyTitle());
        }
        if (propertyRequest.getPropertyImageLink() != null) {
            propertyInfo.setPropertyImageLink(propertyRequest.getPropertyImageLink());
        }
        if (propertyRequest.getPropertyPrice() != null) {
            propertyInfo.setPropertyPrice(propertyRequest.getPropertyPrice());
        }
        if (propertyRequest.getPropertyLocation() != null) {
            propertyInfo.setPropertyLocation(propertyRequest.getPropertyLocation());
        }
        if (!propertyRequest.getPropertyGeoLocation().isEmpty()) {
            propertyInfo.setPropertyGeoLocation(propertyRequest.getPropertyGeoLocation().toString());
        }
        if (!propertyRequest.getPropertyAmenities().isEmpty()) {
            propertyInfo.setPropertyAmenities(propertyRequest.getPropertyAmenities());
        }
    }
}
