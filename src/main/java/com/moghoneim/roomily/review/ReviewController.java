package com.moghoneim.roomily.review;

import com.moghoneim.roomily.review.dto.ReviewRequest;
import com.moghoneim.roomily.review.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody ReviewRequest request) {
        reviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Successfully");
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<ReviewResponse>> getPropertyReviews(
            @PathVariable Long propertyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(reviewService.getPropertyReviews(propertyId, page, size));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsForUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(reviewService.getReviewsForUser(userId, page, size));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByAuthor(
            @PathVariable Long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(reviewService.getReviewsByAuthor(authorId, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

}

