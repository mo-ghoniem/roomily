package com.moghoneim.roomily.booking;

import com.moghoneim.roomily.booking.dto.BookingRequest;
import com.moghoneim.roomily.booking.dto.BookingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@EnableMethodSecurity
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Void> createBooking(@RequestBody BookingRequest bookingRequest) {
        bookingService.create(bookingRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookings() {
        List<BookingResponse> bookings = bookingService.get();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<Void> updateBooking(@PathVariable Long bookingId, @RequestBody BookingRequest bookingRequest) {
        bookingService.update(bookingId, bookingRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_HOST')")
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<String> updateBookingStatus(@PathVariable Long bookingId, @RequestParam String status) {
        String result = bookingService.updateBookingStatus(bookingId, status);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}