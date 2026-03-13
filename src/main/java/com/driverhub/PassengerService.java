
package com.driverhub;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//Service Implementation
@Service
public class PassengerService {
 @Autowired
 private PassengerRepository repo;
 
 @Autowired LocationHistoryRepository historyRepo;

 public Passenger register(Passenger p) { return repo.save(p); }

 public Optional<Passenger> login(String email, String password) {
     return repo.findByEmail(email)
                .filter(p -> p.getPassword().equals(password));
 }

 public List<Passenger> getAll() { return repo.findAll(); }
 public void delete(Long id) { repo.deleteById(id); }
 
 public Passenger getPassengerById(Long id) {
	    return repo.findById(id)
	        .orElseThrow(() -> new PassengerNotFoundException("Passenger not found with id: " + id));
	}
 
 public Passenger updatePassenger(Long id, Passenger passengerDetails) {
	    // 1. Check if passenger exists
	    Passenger existingPassenger = repo.findById(id)
	            .orElseThrow(() -> new PassengerNotFoundException("Passenger not found with id: " + id));

	    // 2. Update the fields
	    existingPassenger.setName(passengerDetails.getName());
	    existingPassenger.setEmail(passengerDetails.getEmail());
	    existingPassenger.setMobile(passengerDetails.getMobile());
	    existingPassenger.setAddress(passengerDetails.getAddress());
	    existingPassenger.setDob(passengerDetails.getDob());
	    
	    // Note: We usually don't update passwords in a general update profile API 
	    // for security reasons, but you can add it if needed.

	    // 3. Save and return
	    return repo.save(existingPassenger);
	}
 
 public Map<String, Double> getPassengerCoordinates(Long id) {
	    Passenger passenger = repo.findById(id)
	            .orElseThrow(() -> new PassengerNotFoundException("Passenger not found with id: " + id));
	    
	    Map<String, Double> coords = new HashMap<>();
	    coords.put("latitude", passenger.getLatitude());
	    coords.put("longitude", passenger.getLongitude());
	    return coords;
	}
 
 public void updateLocation(Long id, Double lat, Double lng) {
	    Passenger passenger = repo.findById(id)
	            .orElseThrow(() -> new PassengerNotFoundException("Passenger not found"));
	    passenger.setLatitude(lat);
	    passenger.setLongitude(lng);
	    repo.save(passenger);
	}
 
 public List<LocationHistory> getRecentRoute(Long passengerId) {
	    LocalDateTime twoHoursAgo = LocalDateTime.now().minusHours(2);
	    return historyRepo.findByPassengerIdAndTimestampAfterOrderByTimestampDesc(passengerId, twoHoursAgo);
	}
}