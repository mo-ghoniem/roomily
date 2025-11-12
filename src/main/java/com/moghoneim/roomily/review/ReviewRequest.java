package com.moghoneim.roomily.review;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReviewRequest {
    private String comment;
    private String rating;
}
