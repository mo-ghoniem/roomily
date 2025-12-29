package com.moghoneim.roomily.property;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.moghoneim.roomily.booking.BookingEntity;
import com.moghoneim.roomily.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="_property")
public class PropertyEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String propertyTitle;
    private String propertyImageLink;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal propertyPrice;

    private String propertyLocation;
    private String propertyGeoLocation;

    private Instant checkInDate;
    private Instant checkOutDate;


    @ElementCollection
    @CollectionTable(name = "property_amenities", joinColumns = @JoinColumn(name = "property_id"))
    private List<String> propertyAmenities;

    @OneToMany(mappedBy = "property",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingEntity> bookings;


    private Boolean isBooked=false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
