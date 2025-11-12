package com.moghoneim.roomily.booking.dto.mapper;


import com.moghoneim.roomily.booking.BookingEntity;
import com.moghoneim.roomily.booking.dto.BookingRequest;
import com.moghoneim.roomily.booking.dto.BookingResponse;
import org.springframework.stereotype.Component;

/**
 * Maps between Booking DTOs and the BookingEntity.
 */
@Component
public class BookingMapper {

    /**
     * Convert an incoming BookingRequest into a new BookingEntity.
     *
     * @param request the client‑facing payload
     * @return a populated (but unsaved) BookingEntity
     */
    public BookingEntity toEntity(BookingRequest request) {
        if (request == null) {
            return null;
        }

        return BookingEntity.builder()
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .numberOfGuests(request.getNumberOfGuests())
                .property(request.getProperty())
                // bookingStatus defaults to "Pending" in the entity, so no need to set it here
                .build();
    }

    /**
     * Convert a BookingEntity into a BookingResponse suitable for returning to the client.
     *
     * @param entity the persisted entity
     * @return a DTO containing all client‑visible booking data
     */
    public BookingResponse toResponse(BookingEntity entity) {
        if (entity == null) {
            return null;
        }

        return BookingResponse.builder()
                .id(entity.getId())
                .checkInDate(entity.getCheckInDate())
                .checkOutDate(entity.getCheckOutDate())
                .numberOfGuests(entity.getNumberOfGuests())
                .bookingStatus(entity.getBookingStatus())
                .property(entity.getProperty())
                .user(entity.getUser())
                .build();
    }
}
