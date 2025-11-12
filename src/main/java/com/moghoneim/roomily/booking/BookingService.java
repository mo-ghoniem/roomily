package com.moghoneim.roomily.booking;

import com.moghoneim.roomily.booking.dto.BookingRequest;
import com.moghoneim.roomily.booking.dto.BookingResponse;

import java.util.List;


public interface BookingService {

    void create(BookingRequest bookingRequest);
    List<BookingResponse> get();
    void update(Long bookingId,BookingRequest bookingRequest);
    void cancelBooking(Long bookingId);
    String updateBookingStatus();
}
