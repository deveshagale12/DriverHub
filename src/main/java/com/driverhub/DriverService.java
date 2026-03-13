

package com.example.Private_CAR_Booking;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepo;

    public Driver registerDriver(Driver driver) {
        driver.setStatus("PENDING"); // Always pending upon registration
        return driverRepo.save(driver);
    }

    public Driver verifyDriver(Long id, String newStatus) {
        Driver driver = driverRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Driver not found"));
        
        // Logical check: only allow CONFIRMED or REJECTED
        if (newStatus.equalsIgnoreCase("CONFIRMED") || newStatus.equalsIgnoreCase("REJECTED")) {
            driver.setStatus(newStatus.toUpperCase());
        }
        return driverRepo.save(driver);
    }
    

    public List<Driver> getPendingDrivers() {
        return driverRepo.findByStatus("PENDING");
    }
    public List<Driver> searchNearbyDrivers(Double passengerLat, Double passengerLng) {
        double radius = 5.0; // 5 Kilometers
        return driverRepo.findNearbyDrivers(passengerLat, passengerLng, radius);
    }
    
    public List<DriverStatusDTO> getNearbyDriversWithETA(Double pLat, Double pLng) {
        double radius = 5.0; // 5km search
        List<Driver> drivers = driverRepo.findNearbyDrivers(pLat, pLng, radius);
        
        double averageCitySpeed = 25.0; // 25 km/h

        return drivers.stream().map(driver -> {
            double dist = calculateDistance(pLat, pLng, driver.getLatitude(), driver.getLongitude());
            
            // Calculate minutes: (Distance / Speed) * 60
            int eta = (int) Math.ceil((dist / averageCitySpeed) * 60);
            
            // Add a 2-minute "buffer" for the driver to start the car/turn around
            eta += 2; 

            DriverStatusDTO dto = new DriverStatusDTO();
            dto.setDriverId(driver.getId());
            dto.setName(driver.getName());
            dto.setDistanceKm(Math.round(dist * 100.0) / 100.0); // Round to 2 decimals
            dto.setEtaMinutes(eta);
            dto.setVehicle(driver.getVehicle());
            return dto;
        }).collect(Collectors.toList());
    }
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Distance in Kilometers using Haversine Formula
        double earthRadius = 6371; 
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
                   
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return earthRadius * c;
    }
    
    
}