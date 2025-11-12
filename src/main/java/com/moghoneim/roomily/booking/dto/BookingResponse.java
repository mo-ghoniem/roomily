package com.moghoneim.roomily.booking.dto;

import com.moghoneim.roomily.property.PropertyEntity;
import com.moghoneim.roomily.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long id;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private int numberOfGuests;
    private String bookingStatus = "Pending";
    private PropertyEntity property;
    private User user;
}
