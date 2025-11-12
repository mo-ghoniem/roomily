package com.moghoneim.roomily.booking;


import com.moghoneim.roomily.property.PropertyEntity;
import com.moghoneim.roomily.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="_booking")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private int numberOfGuests;

    @CreationTimestamp
    @Column(updatable =false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    private String bookingStatus="Pending";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private PropertyEntity property;

    @JoinColumn(name = "user_id", nullable = false)
    private User user;



}
