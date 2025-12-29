package com.moghoneim.roomily.property.dto.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moghoneim.roomily.property.dto.PropertyRequest;
import com.moghoneim.roomily.property.dto.PropertyResponse;
import com.moghoneim.roomily.property.PropertyEntity;
import com.moghoneim.roomily.user.entity.User;
import com.moghoneim.roomily.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PropertyMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
    private final UserRepository userRepository;

    // Method to map PropertyRequest DTO to PropertyInfo Entity
    public PropertyEntity toEntity(PropertyRequest propertyRequest) {


        Instant checkIn = propertyRequest.getCheckInDate() != null
                ? Instant.ofEpochMilli(propertyRequest.getCheckInDate())
                : null;

        Instant checkOut = propertyRequest.getCheckOutDate() != null
                ? Instant.ofEpochMilli(propertyRequest.getCheckOutDate())
                : null;


        String geoLocation;
        try {
            geoLocation = new ObjectMapper().writeValueAsString(propertyRequest.getPropertyGeoLocation());
        } catch (JsonProcessingException e) {
            geoLocation = "-";
        }

        User user = userRepository.findById(propertyRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return PropertyEntity.builder()
                .propertyTitle(propertyRequest.getPropertyTitle())
                .propertyImageLink(propertyRequest.getPropertyImageLink())
                .propertyPrice(propertyRequest.getPropertyPrice())
                .propertyLocation(propertyRequest.getPropertyLocation())
                .propertyGeoLocation(geoLocation)
                .propertyAmenities(propertyRequest.getPropertyAmenities())
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .user(user)
                .build();
    }

    // Method to map PropertyInfo Entity to PropertyResponse DTO
    public PropertyResponse toResponse(PropertyEntity propertyInfo) {

        Map<String, String> geoLocation;
        try {
            geoLocation = new ObjectMapper().readValue(
                    propertyInfo.getPropertyGeoLocation(),
                    new TypeReference<>() {
                    }
            );
        } catch (JsonProcessingException e) {
            geoLocation = Map.of();
        }

        Long checkIn = propertyInfo.getCheckInDate() != null
                ? propertyInfo.getCheckInDate().toEpochMilli()
                : null;

        Long checkOut = propertyInfo.getCheckOutDate() != null
                ? propertyInfo.getCheckOutDate().toEpochMilli()
                : null;

        return PropertyResponse.builder()
                .propertyId(propertyInfo.getId())
                .propertyTitle(propertyInfo.getPropertyTitle())
                .propertyImageLink(propertyInfo.getPropertyImageLink())
                .propertyPrice(propertyInfo.getPropertyPrice())
                .propertyLocation(propertyInfo.getPropertyLocation())
                .propertyGeoLocation(geoLocation)
                .propertyAmenities(propertyInfo.getPropertyAmenities())
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .userId(propertyInfo.getUser().getId())
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


        Instant checkIn = propertyResponse.getCheckInDate() != null
                ? Instant.ofEpochMilli(propertyResponse.getCheckInDate())
                : null;

        Instant checkOut = propertyResponse.getCheckOutDate() != null
                ? Instant.ofEpochMilli(propertyResponse.getCheckOutDate())
                : null;


        User user = userRepository.findById(propertyResponse.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));


        return PropertyEntity.builder()
                .id(propertyResponse.getPropertyId())
                .propertyTitle(propertyResponse.getPropertyTitle())
                .propertyImageLink(propertyResponse.getPropertyImageLink())
                .propertyPrice(propertyResponse.getPropertyPrice())
                .propertyLocation(propertyResponse.getPropertyLocation())
                .propertyGeoLocation(geoLocation)
                .propertyAmenities(propertyResponse.getPropertyAmenities())
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .user(user)
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
        if (propertyRequest.getPropertyGeoLocation() != null && !propertyRequest.getPropertyGeoLocation().isEmpty()) {
            propertyInfo.setPropertyGeoLocation(propertyRequest.getPropertyGeoLocation().toString());
        }
        if (propertyRequest.getPropertyAmenities() != null && !propertyRequest.getPropertyAmenities().isEmpty()) {
            propertyInfo.setPropertyAmenities(propertyRequest.getPropertyAmenities());
        }

        if (propertyRequest.getCheckInDate() != null) {
            propertyInfo.setCheckInDate(Instant.ofEpochMilli(propertyRequest.getCheckInDate()));
        }

        if (propertyRequest.getCheckOutDate() != null) {
            propertyInfo.setCheckOutDate(Instant.ofEpochMilli(propertyRequest.getCheckOutDate()));
        }

    }

}
