package com.example.Private_CAR_Booking;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TripService {
    @Autowired private TripRepository tripRepo;
    @Autowired private DriverRepository driverRepo;

    private static final double CHG_PER_KM = 22.0;

    // 1. Passenger posts a trip
    public Trip postTrip(Trip trip) {
        trip.setStatus("POSTED");
        return tripRepo.save(trip);
    }
    
    private static final double RATE_PER_KM = 22.0;

    // 1. Driver clicks "Start Trip" when passenger is in the car
    public Trip startTrip(Long tripId) {
        Trip trip = tripRepo.findById(tripId).orElseThrow();
        
        // Use the driver's current live location as the starting point
        trip.setStartLat(trip.getDriver().getLatitude());
        trip.setStartLng(trip.getDriver().getLongitude());
        trip.setStartTime(LocalDateTime.now());
        trip.setStatus("STARTED");
        
        return tripRepo.save(trip);
    }

    // 2. Driver clicks "End Trip" at destination
    public Trip completeTrip(Long tripId) {
        Trip trip = tripRepo.findById(tripId).orElseThrow();
        
        // Capture final location
        double endLat = trip.getDriver().getLatitude();
        double endLng = trip.getDriver().getLongitude();
        
        // Calculate Distance using Haversine Formula
        double distance = calculateDistance(trip.getStartLat(), trip.getStartLng(), endLat, endLng);
        
        trip.setTotalKm(distance);
        trip.setFinalFare(distance * RATE_PER_KM);
        trip.setEndTime(LocalDateTime.now());
        trip.setStatus("COMPLETED");
        
        return tripRepo.save(trip);
    }

    // 2. Driver accepts the trip
    public Trip acceptTrip(Long tripId, Long driverId) {
        Trip trip = tripRepo.findById(tripId).orElseThrow();
        Driver driver = driverRepo.findById(driverId).orElseThrow();
        
        if (!"CONFIRMED".equals(driver.getStatus())) throw new RuntimeException("Driver not verified");

        trip.setDriver(driver);
        trip.setStatus("ACCEPTED");
        return tripRepo.save(trip);
    }

    // 3. Complete trip and calculate charges
    public Trip completeTrip(Long tripId, Double actualEndLat, Double actualEndLng) {
        Trip trip = tripRepo.findById(tripId).orElseThrow();
        
        double distance = calculateDistance(trip.getStartLat(), trip.getStartLng(), actualEndLat, actualEndLng);
        
        trip.setTotalKm(distance);
        trip.setFinalFare(distance * CHG_PER_KM);
        trip.setStatus("COMPLETED");
        
        return tripRepo.save(trip);
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
                      Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        return dist * 60 * 1.1515 * 1.609344; // Returns Kilometers
    }
    
}