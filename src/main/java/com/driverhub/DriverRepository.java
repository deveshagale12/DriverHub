
package com.example.Private_CAR_Booking;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long>{
	Optional<Driver> findByEmail(String email);
	List<Driver> findByStatus(String string);
	// Finds CONFIRMED drivers within a specific distance (km) using the Haversine formula
    @Query(value = "SELECT * FROM driver d WHERE d.status = 'CONFIRMED' AND " +
           "(6371 * acos(cos(radians(:lat)) * cos(radians(d.latitude)) * " +
           "cos(radians(d.longitude) - radians(:lng)) + sin(radians(:lat)) * " +
           "sin(radians(d.latitude)))) <= :radius", 
           nativeQuery = true)
    List<Driver> findNearbyDrivers(@Param("lat") Double lat, 
                                   @Param("lng") Double lng, 
                                   @Param("radius") Double radius);
 // You can also keep this for lookups
    Optional<Driver> findByLicenseNumber(String licenseNumber);
}
