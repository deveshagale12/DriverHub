
package com.example.Private_CAR_Booking;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BookingService {
    @Autowired private BookingRepository bookingRepo;
    @Autowired private PassengerRepository passengerRepo;
    @Autowired private DriverRepository driverRepo;

    public Booking createBooking(Long passengerId, Long driverId, String pickup, String drop) {
        Passenger passenger = passengerRepo.findById(passengerId)
            .orElseThrow(() -> new RuntimeException("Passenger not found"));
        
        Driver driver = driverRepo.findById(driverId)
            .orElseThrow(() -> new RuntimeException("Driver not found"));

        // Business Rule: Driver must be verified by Admin first
        if (!"CONFIRMED".equals(driver.getStatus())) {
            throw new RuntimeException("Driver is not verified and cannot accept rides.");
        }

        Booking booking = new Booking();
        booking.setPassenger(passenger);
        booking.setDriver(driver);
        booking.setPickupLocation(pickup);
        booking.setDropLocation(drop);
        booking.setBookingStatus("REQUESTED");
        booking.setBookingTime(LocalDateTime.now());
        booking.setFare(150.0); // Example static fare

        return bookingRepo.save(booking);
    }
}