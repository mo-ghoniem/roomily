package com.moghoneim.roomily.property.impl;

import com.moghoneim.roomily.api.PropertyApiClient;
import com.moghoneim.roomily.property.dto.PropertyRequest;
import com.moghoneim.roomily.property.dto.PropertyResponse;
import com.moghoneim.roomily.property.dto.mapper.PropertyMapper;
import com.moghoneim.roomily.property.PropertyEntity;
import com.moghoneim.roomily.property.PropertyRepository;
import com.moghoneim.roomily.property.PropertyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final PropertyApiClient propertyApiClient;

    // Create a new property
    @Override
    public String createProperty(PropertyRequest propertyRequest) {
        try {
            // Convert PropertyRequest to PropertyInfo entity
            PropertyEntity propertyInfo = propertyMapper.toEntity(propertyRequest);

            // Save the property entity in the database
            propertyRepository.save(propertyInfo);

            return "Property successfully created.";
        } catch (Exception e) {
            return "Error occurred while creating property: " + e.getMessage();
        }
    }

    // Get a property by its ID
    @Override
    public PropertyResponse getPropertyById(Long propertyId) {
        Optional<PropertyEntity> propertyInfoOptional = propertyRepository.findById(propertyId);

        if (propertyInfoOptional.isPresent()) {
            // Convert the found entity to PropertyResponse DTO
            return propertyMapper.toResponse(propertyInfoOptional.get());
        } else {
            throw new RuntimeException("Property not found with ID: " + propertyId);
        }
    }

    // Get all properties
    @Override
    public List<PropertyResponse> getAllProperties() {
        List<PropertyEntity> propertyList = propertyRepository.findAll();

        // Convert the list of PropertyInfo entities to PropertyResponse DTOs
        return propertyList.stream()
                .map(propertyMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Update an existing property
    @Override
    public String updateProperty(Long propertyId, PropertyRequest propertyRequest) {
        Optional<PropertyEntity> propertyInfoOptional = propertyRepository.findById(propertyId);

        if (propertyInfoOptional.isPresent()) {
            PropertyEntity existingProperty = propertyInfoOptional.get();

            // Use the mapper to update the existing property with new data
            propertyMapper.updateEntityFromRequest(propertyRequest, existingProperty);

            // Save the updated property back to the database
            propertyRepository.save(existingProperty);

            return "Property successfully updated.";
        } else {
            return "Property not found with ID: " + propertyId;
        }
    }

    // Delete a property by its ID
    @Override
    public String deleteProperty(Long propertyId) {
        Optional<PropertyEntity> propertyInfoOptional = propertyRepository.findById(propertyId);

        if (propertyInfoOptional.isPresent()) {
            // Delete the property from the database
            propertyRepository.delete(propertyInfoOptional.get());
            return "Property successfully deleted.";
        } else {
            return "Property not found with ID: " + propertyId;
        }
    }

    @Override
    public List<PropertyResponse> getExtractedPropertiesByLocation(String location) {
        try {
            return propertyApiClient.getExtractedPropertiesByLocation(location);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching properties from external API", e);
        }
    }


    // Save the extracted properties to the database
    @Override
    public String saveExtractedProperties(String location) {
        try {
            List<PropertyResponse> propertyResponses = getExtractedPropertiesByLocation(location);

            if (propertyResponses.isEmpty()) {
                return "No properties found to save.";
            }

            for (PropertyResponse propertyResponse : propertyResponses) {
                // Save the property into the database
                propertyRepository.save(propertyMapper.fromResponse(propertyResponse));
            }

            return "Properties successfully saved.";
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Error saving properties to the database", e);
        }
    }


}
