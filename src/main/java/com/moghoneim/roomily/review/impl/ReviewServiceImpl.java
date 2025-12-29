package com.moghoneim.roomily.review.impl;

import com.moghoneim.roomily.exception.AccessDeniedException;
import com.moghoneim.roomily.exception.ReviewDeletionNotAllowedException;
import com.moghoneim.roomily.exception.ReviewNotFoundException;
import com.moghoneim.roomily.review.ReviewEntity;
import com.moghoneim.roomily.review.ReviewRepository;
import com.moghoneim.roomily.review.ReviewService;
import com.moghoneim.roomily.review.ReviewTargetType;
import com.moghoneim.roomily.review.dto.ReviewRequest;
import com.moghoneim.roomily.review.dto.ReviewResponse;
import com.moghoneim.roomily.review.dto.mapper.ReviewMapper;
import com.moghoneim.roomily.security.CustomUserDetails;
import com.moghoneim.roomily.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public void createReview(ReviewRequest request) {
        ReviewEntity reviewEntity = ReviewEntity.builder()
                .comment(request.getComment())
                .rating(request.getRating())
                .author(User.builder().id(request.getReviewerId()).build())
                .targetUser(User.builder().id(request.getTargetId()).build())
                .reviewType(request.getTargetType())
                .build();
        reviewRepository.save(reviewEntity);
    }

    @Override
    public List<ReviewResponse> getPropertyReviews(Long propertyId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository
                .findByProperty_IdAndReviewType(propertyId, ReviewTargetType.PROPERTY, pageable)
                .stream().map(reviewMapper::toResponse)
                .toList();
    }

    @Override
    public List<ReviewResponse> getReviewsForUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository
                .findByTargetUserId(userId, pageable)
                .stream().map(reviewMapper::toResponse)
                .toList();
    }

    @Override
    public List<ReviewResponse> getReviewsByAuthor(Long authorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository
                .findByAuthorId(authorId, pageable)
                .stream().map(reviewMapper::toResponse)
                .toList();
    }

    @Override
    public ReviewResponse getReviewById(Long id) {
        ReviewEntity reviewEntity = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review with this id not found: " + id));
        return reviewMapper.toResponse(reviewEntity);
    }

    @Override
    public void deleteReview(Long id) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review with this id not found: " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        if (!review.getAuthor().getId().equals(userDetails.getId())) {
            throw new AccessDeniedException("You can only delete your own reviews");
        }

        Duration duration = Duration.between(
                review.getCreatedAt(),
                LocalDateTime.now()
        );

        if (duration.toHours() > 48) {
            throw new ReviewDeletionNotAllowedException("Review can only be deleted within 48 hours");
        }

        reviewRepository.delete(review);
    }
}
