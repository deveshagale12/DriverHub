
package com.example.Private_CAR_Booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    @Autowired
    private DriverService service;
    
    @Autowired
    private DriverRepository driverRepo;

    // Driver: Register profile and vehicle
    @PostMapping("/register")
    public Driver register(@RequestBody Driver driver) {
        return service.registerDriver(driver);
    }

    // Admin: Get all drivers waiting for approval
    @GetMapping("/admin/pending")
    public List<Driver> getPending() {
        return service.getPendingDrivers();
    }

    // Admin: Verify (Confirm/Reject) a driver
    @PatchMapping("/admin/verify/{id}")
    public ResponseEntity<String> verify(@PathVariable Long id, @RequestParam String status) {
        service.verifyDriver(id, status);
        return ResponseEntity.ok("Driver status updated to: " + status);
    }
    @PatchMapping("/{id}/update-location")
    public ResponseEntity<String> updateDriverLocation(
            @PathVariable Long id, 
            @RequestParam Double lat, 
            @RequestParam Double lng) {
        
        Driver driver = driverRepo.findById(id).orElseThrow();
        driver.setLatitude(lat);
        driver.setLongitude(lng);
        driverRepo.save(driver);
        
        return ResponseEntity.ok("Location updated");
    }
    
    @GetMapping("/nearby")
    public ResponseEntity<List<Driver>> getNearbyDrivers(
            @RequestParam Double lat, 
            @RequestParam Double lng) {
        
        List<Driver> nearby = service.searchNearbyDrivers(lat, lng);
        return ResponseEntity.ok(nearby);
    }
    
    @GetMapping("/nearby-eta")
    public ResponseEntity<List<DriverStatusDTO>> getNearbyWithETA(
            @RequestParam Double lat, 
            @RequestParam Double lng) {
        
        return ResponseEntity.ok(service.getNearbyDriversWithETA(lat, lng));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        return driverRepo.findByEmail(email)
                .map(driver -> {
                    // Check password (In a real app, use BCrypt for this)
                    if (!driver.getPassword().equals(password)) {
                        return ResponseEntity.status(401).body("Invalid Password");
                    }
                    
                    // Block if not approved by Admin
                    if ("PENDING".equals(driver.getStatus())) {
                        return ResponseEntity.status(403).body("Account pending admin approval");
                    }
                    
                    return ResponseEntity.ok(driver);
                })
                .orElse(ResponseEntity.status(404).body("Email not found"));
    }
}