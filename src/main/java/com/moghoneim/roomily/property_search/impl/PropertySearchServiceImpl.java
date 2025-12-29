package com.moghoneim.roomily.property_search.impl;

import com.moghoneim.roomily.property.PropertyEntity;
import com.moghoneim.roomily.property.PropertyRepository;
import com.moghoneim.roomily.property.dto.PropertyResponse;
import com.moghoneim.roomily.property_search.PropertySearchService;
import com.moghoneim.roomily.property_search.dto.PropertySearchRequest;
import com.moghoneim.roomily.property_search.spec.PropertySpecifications;
import com.moghoneim.roomily.property.dto.mapper.PropertyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertySearchServiceImpl implements PropertySearchService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;

    @Override
    public List<PropertyResponse> propertySearch(PropertySearchRequest request) {

        // Validation
        if (request.getMinPrice() != null && request.getMaxPrice() != null
                && request.getMinPrice().compareTo(request.getMaxPrice()) > 0) {
            throw new IllegalArgumentException("minPrice cannot be greater than maxPrice");
        }

        if (request.getCheckIn() != null && request.getCheckOut() != null
                && request.getCheckIn() > request.getCheckOut()) {
            throw new IllegalArgumentException("checkIn cannot be after checkOut");
        }

        // Build dynamic specification
        Specification<PropertyEntity> spec = Specification.where(PropertySpecifications.titleContains(request.getTitle()))
                .and(PropertySpecifications.locationContains(request.getLocation()))
                .and(PropertySpecifications.priceBetween(request.getMinPrice(), request.getMaxPrice()))
                .and(PropertySpecifications.checkInAfter(request.getCheckIn()))
                .and(PropertySpecifications.checkOutBefore(request.getCheckOut()));

        List<PropertyResponse> results = propertyRepository.findAll(spec)
                .stream()
                .map(propertyMapper::toResponse)
                .toList();

        if (results.isEmpty()) {
            throw new RuntimeException("No properties match your search filters");
        }

        return results;
    }
}
