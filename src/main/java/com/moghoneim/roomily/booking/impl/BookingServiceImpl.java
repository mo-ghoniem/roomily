package com.moghoneim.roomily.booking.impl;

import com.moghoneim.roomily.booking.BookingEntity;
import com.moghoneim.roomily.booking.BookingRepository;
import com.moghoneim.roomily.booking.BookingService;
import com.moghoneim.roomily.booking.dto.BookingRequest;
import com.moghoneim.roomily.booking.dto.BookingResponse;
import com.moghoneim.roomily.booking.dto.mapper.BookingMapper;
import com.moghoneim.roomily.exception.BookingNotFoundException;
import com.moghoneim.roomily.exception.UnauthenticatedUserException;
import com.moghoneim.roomily.property.PropertyEntity;
import com.moghoneim.roomily.property.PropertyRepository;
import com.moghoneim.roomily.security.CustomUserDetails;
import com.moghoneim.roomily.user.dto.UserDto;
import com.moghoneim.roomily.user.dto.mapper.UserMapper;
import com.moghoneim.roomily.user.entity.User;
import com.moghoneim.roomily.user.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    private final BookingMapper bookingMapper;
    private final UserMapper userMapper;
    private final UserServiceImpl userService;



    @Transactional
    public void create(BookingRequest bookingRequest) {
        // Validate input
        if (bookingRequest == null || bookingRequest.getProperty() == null || bookingRequest.getProperty().getId() == null) {
            throw new IllegalArgumentException("Booking request or property ID cannot be null");
        }

        // Step 1: Get the property info and validate
        PropertyEntity property = propertyRepository.findById(bookingRequest.getProperty().getId())
                .orElseThrow(() -> new EntityNotFoundException("Property not found with ID: " + bookingRequest.getProperty().getId()));

        // Step 2: Check if the property is already booked
        if (property.getIsBooked()) {
            throw new IllegalStateException("Property is already booked");
        }

        // Step 3: Check if the user is trying to book their own property
        UserDto currentUser = userService.getCurrentUser();
        if (property.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("Cannot book your own property");
        }

        // Step 4: Create the booking
        BookingEntity booking = bookingMapper.toEntity(bookingRequest);
        booking.setUser(userMapper.toEntity(userService.getCurrentUser())); // Set the authenticated user
        booking.setProperty(property); // Ensure the property is set
        booking.setBookingStatus("Pending"); // Explicitly set, though defaulted in entity

        // Step 5: Mark the property as booked
        property.setIsBooked(true);

        // Step 6: Save the booking and update the property
        bookingRepository.save(booking);
        propertyRepository.save(property);
    }

    @Override
    public List<BookingResponse> get() {
        UserDto user = userService.getCurrentUser();
        List<BookingResponse> bookings =  bookingRepository.findByUserId(user.getId()).stream()
                .map(bookingMapper::toResponse).toList();;
        return bookings;
    }

    // Method for guest
    @Transactional
    public void update(Long bookingId, BookingRequest bookingRequest) {
        // Validate input
        if (bookingId == null || bookingRequest == null) {
            throw new IllegalArgumentException("Booking ID or booking request cannot be null");
        }

        // Step 1: Retrieve the booking
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + bookingId));

        // Step 2: Check if the booking is older than 24 hours
        LocalDateTime now = LocalDateTime.now();
        long hoursSinceCreation = ChronoUnit.HOURS.between(booking.getCreatedAt(), now);
        if (hoursSinceCreation > 24) {
            throw new IllegalStateException("Cannot update booking: More than 24 hours have passed since creation");
        }

        // Step 3: Update only the allowed fields
        if (bookingRequest.getCheckInDate() != null) {
            booking.setCheckInDate(bookingRequest.getCheckInDate());
        }
        if (bookingRequest.getCheckOutDate() != null) {
            booking.setCheckOutDate(bookingRequest.getCheckOutDate());
        }
        if (bookingRequest.getNumberOfGuests() != 0) {
            booking.setNumberOfGuests(bookingRequest.getNumberOfGuests());
        }

        // Step 4: Save the updated booking
        bookingRepository.save(booking);
    }

    // Method for guest
    @Override
    public void cancelBooking(Long bookingId) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingId));

        bookingRepository.delete(booking);
    }


    // Method for host
    @Override
    public String updateBookingStatus() {

        return "";
    }


}
