package com.moghoneim.roomily.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Page<ReviewEntity> findByProperty_IdAndReviewType(Long propertyId, ReviewTargetType reviewType, Pageable pageable);
    Page<ReviewEntity> findByTargetUserId(Long userId, Pageable pageable);
    Page<ReviewEntity> findByAuthorId(Long authorId, Pageable pageable);
}
