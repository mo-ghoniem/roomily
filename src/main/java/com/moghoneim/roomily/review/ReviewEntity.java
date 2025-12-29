package com.moghoneim.roomily.review;

import com.moghoneim.roomily.property.PropertyEntity;
import com.moghoneim.roomily.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Integer rating;

    // Who wrote the review
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    //(host or guest)
    @ManyToOne
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewTargetType reviewType; // PROPERTY / HOST / GUEST

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private PropertyEntity property;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

