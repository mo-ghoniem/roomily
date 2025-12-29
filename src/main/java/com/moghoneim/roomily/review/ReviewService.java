package com.moghoneim.roomily.review;

import com.moghoneim.roomily.review.dto.ReviewRequest;
import com.moghoneim.roomily.review.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {

    void createReview(ReviewRequest request);

    List<ReviewResponse> getPropertyReviews(Long propertyId, int page, int size);

    List<ReviewResponse> getReviewsForUser(Long userId, int page, int size);

    List<ReviewResponse> getReviewsByAuthor(Long authorId, int page, int size);

    ReviewResponse getReviewById(Long id);

    void deleteReview(Long id);
}

