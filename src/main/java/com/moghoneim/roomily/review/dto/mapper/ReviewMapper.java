package com.moghoneim.roomily.review.dto.mapper;

import com.moghoneim.roomily.review.ReviewEntity;
import com.moghoneim.roomily.review.dto.ReviewRequest;
import com.moghoneim.roomily.review.dto.ReviewResponse;
import com.moghoneim.roomily.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewEntity toEntity(ReviewRequest reviewRequest){

        return ReviewEntity.builder()
                .comment(reviewRequest.getComment())
                .rating(reviewRequest.getRating())
                .author(User.builder().id(reviewRequest.getReviewerId()).build())
                .targetUser(User.builder().id(reviewRequest.getTargetId()).build())
                .reviewType(reviewRequest.getTargetType())
                .build();
    }

    public ReviewResponse toResponse(ReviewEntity reviewEntity){

        return ReviewResponse.builder()
                .id(reviewEntity.getId())
                .comment(reviewEntity.getComment())
                .rating(reviewEntity.getRating())
                .reviewerId(reviewEntity.getAuthor().getId())
                .targetId(reviewEntity.getTargetUser().getId())
                .targetType(reviewEntity.getReviewType())
                .createdAt(reviewEntity.getCreatedAt())
                .build();
    }
}
