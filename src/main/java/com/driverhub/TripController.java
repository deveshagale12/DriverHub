package com.example.Private_CAR_Booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    @Autowired private TripService service;
    @Autowired private TripRepository tripRepo;

    @PostMapping("/post")
    public Trip post(@RequestBody Trip trip) {
        return service.postTrip(trip);
    }

    @GetMapping("/available")
    public List<Trip> getAvailable() {
        return tripRepo.findByStatus("POSTED");
    }

    @PatchMapping("/{tripId}/accept/{driverId}")
    public Trip accept(@PathVariable Long tripId, @PathVariable Long driverId) {
        return service.acceptTrip(tripId, driverId);
    }

    @PatchMapping("/{tripId}/start")
    public ResponseEntity<Trip> start(@PathVariable Long tripId) {
        return ResponseEntity.ok(service.startTrip(tripId));
    }

    // ONLY KEEP THIS ONE COMPLETE METHOD
    // It uses the driver's current coordinates already stored in the Driver entity
    @PatchMapping("/{tripId}/complete")
    public ResponseEntity<Trip> complete(@PathVariable Long tripId) {
        return ResponseEntity.ok(service.completeTrip(tripId));
    }
}