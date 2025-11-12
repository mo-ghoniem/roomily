package com.moghoneim.roomily.property;

import com.moghoneim.roomily.property.dto.PropertyRequest;
import com.moghoneim.roomily.property.dto.PropertyResponse;
import com.moghoneim.roomily.property.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    private final PropertyService propertyService;

    // Create a new property
    @PostMapping("/create-property")
    public ResponseEntity<String> createProperty(@RequestBody PropertyRequest propertyRequest) {
        String responseMessage = propertyService.createProperty(propertyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    // Get a property by its ID
    @GetMapping("/search-property/{property-id}")
    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable("property-id") Long propertyId) {
        try {
            PropertyResponse propertyResponse = propertyService.getPropertyById(propertyId);
            return ResponseEntity.ok(propertyResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Get all properties
    @GetMapping("/get-all-properties")
    public ResponseEntity<List<PropertyResponse>> getAllProperties() {
        List<PropertyResponse> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties);
    }

    // Update an existing property
    @PutMapping("/update-property/{property-id}")
    public ResponseEntity<String> updateProperty(@PathVariable("property-id") Long propertyId, @RequestBody PropertyRequest propertyRequest) {
        String responseMessage = propertyService.updateProperty(propertyId, propertyRequest);
        if (responseMessage.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        return ResponseEntity.ok(responseMessage);
    }

    // Delete a property by its ID
    @DeleteMapping("/delete-property/{property-id}")
    public ResponseEntity<String> deleteProperty(@PathVariable("property-id") Long propertyId) {
        String responseMessage = propertyService.deleteProperty(propertyId);
        if (responseMessage.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/api-property-data/{location}")
    public ResponseEntity<List<PropertyResponse>> getExtractedPropertiesByLocation(@PathVariable String location) {
        try {
            // Call the service to fetch properties by location
            List<PropertyResponse> propertyResponses = propertyService.getExtractedPropertiesByLocation(location);
            return ResponseEntity.ok(propertyResponses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint to save extracted properties based on location (POST)
    @PostMapping("/save-api-property-data/{location}")
    public ResponseEntity<String> saveExtractedProperties(@PathVariable String location) {
        try {
            // Call the service to save properties
            String result = propertyService.saveExtractedProperties(location);
            return ResponseEntity.ok(result);  // Return success message
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving properties to the database");  // Handle error
        }
    }
}
