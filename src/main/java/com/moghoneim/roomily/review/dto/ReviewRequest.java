package com.moghoneim.roomily.review.dto;

import com.moghoneim.roomily.review.ReviewTargetType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReviewRequest {

    @NotNull
    private Long reviewerId;  // The user writing the review

    @NotNull
    private Long targetId;    // Property ID or Host ID

    @NotNull
    private ReviewTargetType targetType; // PROPERTY, HOST, GUEST

    @Min(1)
    @Max(5)
    private int rating;

    @Size(max = 500)
    private String comment;

}

