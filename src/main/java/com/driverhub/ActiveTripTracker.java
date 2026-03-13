package com.example.Private_CAR_Booking;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ActiveTripTracker {

    private final TripRepository tripRepository;
    private final LocationHistoryRepository historyRepo;

    public ActiveTripTracker(TripRepository tripRepository, LocationHistoryRepository historyRepo) {
        this.tripRepository = tripRepository;
        this.historyRepo = historyRepo;
    }

    @Scheduled(fixedRate = 30000) // Runs every 30 seconds
    public void trackActiveDrivers() {
        // 1. Only find trips that are currently active
        List<String> activeStatuses = List.of("ACCEPTED", "STARTED");
        List<Trip> activeTrips = tripRepository.findByStatusIn(activeStatuses);

        if (activeTrips.isEmpty()) {
            return; // Do nothing if no active bookings
        }

        activeTrips.forEach(trip -> {
            Driver driver = trip.getDriver();
            if (driver != null && driver.getLatitude() != null) {
                // 2. Save the driver's current position to history
                LocationHistory history = new LocationHistory(
                    driver.getId(), 
                    driver.getLatitude(), 
                    driver.getLongitude(), 
                    LocalDateTime.now()
                );
                historyRepo.save(history);
                
                System.out.println("Tracking Driver " + driver.getName() + " for Trip #" + trip.getId());
            }
        });
    }
}