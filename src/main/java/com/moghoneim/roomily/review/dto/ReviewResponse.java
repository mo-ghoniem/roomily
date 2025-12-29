package com.moghoneim.roomily.review.dto;

import com.moghoneim.roomily.review.ReviewTargetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private Long id;
    private int rating;
    private String comment;
    private Long reviewerId;
    private Long targetId;
    private ReviewTargetType targetType;
    private LocalDateTime createdAt;
    private String reviewerName;
    private String targetName;

}
