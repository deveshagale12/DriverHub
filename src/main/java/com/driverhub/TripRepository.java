
package com.example.Private_CAR_Booking;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TripRepository extends JpaRepository<Trip, Long>{
	    List<Trip> findByPassengerIdOrderByIdDesc(Long passengerId);
	    List<Trip> findByStatus(String status);
	    List<Trip> findByStatusIn(List<String> statuses);
	    List<Trip> findByStatusAndPostedTimeBefore(String status, java.time.LocalDateTime time);
	

}
