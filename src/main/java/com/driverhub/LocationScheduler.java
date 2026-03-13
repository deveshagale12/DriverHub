
package com.example.Private_CAR_Booking;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class LocationScheduler {

    private final PassengerService passengerService;
    private final LocationHistoryRepository historyRepo;

    public LocationScheduler(PassengerService passengerService, LocationHistoryRepository historyRepo) {
        this.passengerService = passengerService;
        this.historyRepo = historyRepo;
    }

    // Task 1: Record History every 30 seconds
    @Scheduled(fixedRate = 30000)
    public void recordLocationHistory() {
        List<Passenger> passengers = passengerService.getAll();
        
        passengers.forEach(p -> {
            if (p.getLatitude() != null && p.getLongitude() != null) {
                LocationHistory history = new LocationHistory(
                    p.getId(), p.getLatitude(), p.getLongitude(), LocalDateTime.now()
                );
                historyRepo.save(history);
            }
        });
        System.out.println("Location snapshots recorded at " + LocalDateTime.now());
    }

    // Task 2: Clean up data older than 2 days (48 hours)
    // Runs once every hour to keep the database light
    @Transactional
    @Scheduled(fixedRate = 3600000) 
    public void cleanOldHistory() {
        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2);
        historyRepo.deleteByTimestampBefore(twoDaysAgo);
        System.out.println("Cleaned up location history older than: " + twoDaysAgo);
    }
}