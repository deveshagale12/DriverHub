
package com.example.Private_CAR_Booking;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
@Component
public class TripTimeoutHandler {

    private final TripRepository tripRepo;

    public TripTimeoutHandler(TripRepository tripRepo) {
        this.tripRepo = tripRepo;
    }

    // Runs every 30 seconds to check for expired trips
    @Scheduled(fixedRate = 30000)
    @Transactional
    public void handleExpiredTrips() {
        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);
        
        // Find trips still in 'POSTED' status created more than 60 seconds ago
        List<Trip> expiredTrips = tripRepo.findByStatusAndPostedTimeBefore("POSTED", oneMinuteAgo);
        
        if (!expiredTrips.isEmpty()) {
            expiredTrips.forEach(trip -> {
                trip.setStatus("EXPIRED");
                System.out.println("Trip #" + trip.getId() + " timed out - No driver accepted.");
            });
            tripRepo.saveAll(expiredTrips);
        }
    }
}