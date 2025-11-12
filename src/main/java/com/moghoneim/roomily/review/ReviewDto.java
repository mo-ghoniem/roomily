package com.moghoneim.roomily.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private String comment;
    private Integer rating;
    private Long propertyId; // Use property ID instead of the entire Property object
    private Long userId;     // Use user ID instead of the entire User object
    private LocalDateTime createdAt;
}