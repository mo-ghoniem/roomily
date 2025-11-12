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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Integer rating;
//
//    @ManyToOne
//    @JoinColumn(name = "property_id", nullable = false)
//    private PropertyEntity property;

//    @ManyToMany
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
