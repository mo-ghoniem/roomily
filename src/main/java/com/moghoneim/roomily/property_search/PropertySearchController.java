package com.moghoneim.roomily.property_search;

import com.moghoneim.roomily.property.dto.PropertyResponse;
import com.moghoneim.roomily.property_search.PropertySearchService;
import com.moghoneim.roomily.property_search.dto.PropertySearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/properties")
public class PropertySearchController {

    private final PropertySearchService propertySearchService;

    @GetMapping("/search")
    public ResponseEntity<List<PropertyResponse>> searchProperties(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Long checkIn,
            @RequestParam(required = false) Long checkOut
    ) {
        PropertySearchRequest request = PropertySearchRequest.builder()
                .title(title)
                .location(location)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .build();

        List<PropertyResponse> results = propertySearchService.propertySearch(request);
        return ResponseEntity.ok(results);
    }

}
