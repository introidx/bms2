package com.introidx.bms2.controller;

import com.introidx.bms2.dto.BookTicketsDTO;
import com.introidx.bms2.dto.CheckAvailabilityDTO;
import com.introidx.bms2.entity.BMSEvents;
import com.introidx.bms2.entity.Venue;
import com.introidx.bms2.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/get-available-seats/{eventId}")
    public ResponseEntity<Venue> getAvailableSeats(@PathVariable Long eventId) {
        return ResponseEntity.ok(bookingService.getSeats(eventId));
    }

    @GetMapping("/check-availability")
    public ResponseEntity<?> checkAvailability(@RequestBody CheckAvailabilityDTO checkAvailabilityDTO) {
        return ResponseEntity.ok(bookingService.checkAvailability(checkAvailabilityDTO));
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookEvent(@RequestBody BookTicketsDTO bookTicketsDTO) {
        return ResponseEntity.ok(bookingService.bookEvent(bookTicketsDTO));
    }

    @GetMapping("/get-all-tickets/{eventId}")
    public ResponseEntity<?> getAllTickets(@PathVariable Long eventId) {
        return ResponseEntity.ok(bookingService.getAllTickets(eventId));
    }
}
