
package com.driverhub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<Booking> bookRide(
            @RequestParam Long passengerId, 
            @RequestParam Long driverId,
            @RequestParam String pickup,
            @RequestParam String drop) {
        
        Booking booking = bookingService.createBooking(passengerId, driverId, pickup, drop);
        return ResponseEntity.ok(booking);
    }
}