package com.moghoneim.roomily.property;

import com.moghoneim.roomily.property.dto.PropertyRequest;
import com.moghoneim.roomily.property.dto.PropertyResponse;

import java.util.List;

public interface PropertyService {
    // Create a new property
    String createProperty(PropertyRequest propertyRequest);

    // Get a property by its ID
    PropertyResponse getPropertyById(Long propertyId);

    // Get all properties
    List<PropertyResponse> getAllProperties();

    // Update a property
    String updateProperty(Long propertyId, PropertyRequest propertyRequest);

    // Delete a property by its ID
    String deleteProperty(Long propertyId);

    List<PropertyResponse> getExtractedPropertiesByLocation(String location);


    String saveExtractedProperties(String location);

}
