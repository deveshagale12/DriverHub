package com.example.Private_CAR_Booking;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/stats")
public class AdminStatsController {
    @Autowired private TripRepository tripRepo;
    @Autowired private DriverRepository driverRepo;

    @GetMapping("/summary")
    public ResponseEntity<?> getSummary() {
        long totalTrips = tripRepo.count();
        long activeDrivers = driverRepo.findByStatus("CONFIRMED").size();
        
        // Sum of all finalFare where status is COMPLETED
        Double totalRevenue = tripRepo.findAll().stream()
                .filter(t -> "COMPLETED".equals(t.getStatus()))
                .mapToDouble(t -> t.getFinalFare() != null ? t.getFinalFare() : 0.0)
                .sum();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalTrips", totalTrips);
        stats.put("activeDrivers", activeDrivers);
        stats.put("totalRevenue", totalRevenue);
        stats.put("commission", totalRevenue * 0.10); // Assuming 10% platform fee
        
        return ResponseEntity.ok(stats);
    }
}