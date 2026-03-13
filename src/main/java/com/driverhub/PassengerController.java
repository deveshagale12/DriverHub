
package com.example.Private_CAR_Booking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerService service;
    
    @Autowired 
    private TripRepository tripRepo;

    // 1. REGISTER
    @PostMapping("/register")
    public Passenger register(@RequestBody Passenger p) {
        return service.register(p);
    }

    // 2. UPDATED LOGIN (Returns Passenger object so frontend knows the ID)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        return service.login(email, password)
                .map(passenger -> ResponseEntity.ok(passenger)) // Return JSON of Passenger
                .orElse(ResponseEntity.status(401).body(null));
    }

    // 3. FETCH ALL (For Admin use)
    @GetMapping
    public List<Passenger> getAll() { 
        return service.getAll(); 
    }
    
    // 4. GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        Passenger passenger = service.getPassengerById(id);
        return ResponseEntity.ok(passenger);
    }

    // 5. UPDATE PROFILE
    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody Passenger passengerDetails) {
        Passenger updatedPassenger = service.updatePassenger(id, passengerDetails);
        return ResponseEntity.ok(updatedPassenger);
    }
    
    // 6. UPDATE LIVE LOCATION (Called by Passenger App)
    @PatchMapping("/{id}/location")
    public ResponseEntity<String> updateLocation(
            @PathVariable Long id, 
            @RequestParam Double lat, 
            @RequestParam Double lng) {
        service.updateLocation(id, lat, lng);
        return ResponseEntity.ok("Location updated successfully");
    }

    // 7. GET LIVE DRIVER LOCATION (For the active ride map)
    @GetMapping("/trips/{tripId}/driver-location")
    public ResponseEntity<Map<String, Double>> getLiveDriverLocation(@PathVariable Long tripId) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        // Only allow tracking if the ride is live
        if (!List.of("ACCEPTED", "STARTED").contains(trip.getStatus())) {
            return ResponseEntity.badRequest().body(null);
        }

        Driver d = trip.getDriver();
        Map<String, Double> location = new HashMap<>();
        location.put("lat", d.getLatitude());
        location.put("lng", d.getLongitude());
        
        return ResponseEntity.ok(location);
    }

    // 8. GET PASSENGER TRIP HISTORY
    @GetMapping("/{id}/trips")
    public ResponseEntity<List<Trip>> getMyTrips(@PathVariable Long id) {
        // Find all trips where passenger ID matches
        return ResponseEntity.ok(tripRepo.findByPassengerIdOrderByIdDesc(id));
    }

    // 9. GET ROUTE HISTORY (Breadcrumbs for safety)
    @GetMapping("/{id}/route")
    public ResponseEntity<List<LocationHistory>> getPassengerRoute(@PathVariable Long id) {
        List<LocationHistory> route = service.getRecentRoute(id);
        return ResponseEntity.ok(route);
    }
}