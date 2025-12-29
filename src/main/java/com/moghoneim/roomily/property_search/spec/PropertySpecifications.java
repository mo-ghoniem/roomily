package com.moghoneim.roomily.property_search.spec;

import com.moghoneim.roomily.property.PropertyEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;

public class PropertySpecifications {

    public static Specification<PropertyEntity> titleContains(String title) {
        return (root, query, cb) ->
                title == null ? null :
                        cb.like(cb.lower(root.get("propertyTitle")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<PropertyEntity> locationContains(String location) {
        return (root, query, cb) ->
                location == null ? null :
                        cb.like(cb.lower(root.get("propertyLocation")), "%" + location.toLowerCase() + "%");
    }

    public static Specification<PropertyEntity> priceBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;

            if (min != null && max != null)
                return cb.between(root.get("propertyPrice"), min, max);

            if (min != null)
                return cb.greaterThanOrEqualTo(root.get("propertyPrice"), min);

            return cb.lessThanOrEqualTo(root.get("propertyPrice"), max);
        };
    }


    public static Specification<PropertyEntity> checkInAfter(Long checkInEpoch) {
        return (root, query, cb) ->
                checkInEpoch == null ? null :
                        cb.greaterThanOrEqualTo(root.get("checkInDate"), Instant.ofEpochMilli(checkInEpoch));
    }

    public static Specification<PropertyEntity> checkOutBefore(Long checkOutEpoch) {
        return (root, query, cb) ->
                checkOutEpoch == null ? null :
                        cb.lessThanOrEqualTo(root.get("checkOutDate"), Instant.ofEpochMilli(checkOutEpoch));
    }
}
