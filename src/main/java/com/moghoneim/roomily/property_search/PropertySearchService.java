package com.moghoneim.roomily.property_search;

import com.moghoneim.roomily.property.dto.PropertyResponse;
import com.moghoneim.roomily.property_search.dto.PropertySearchRequest;

import java.util.List;

public interface PropertySearchService {

    List<PropertyResponse> propertySearch(PropertySearchRequest propertySearchRequest);
}
