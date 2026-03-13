
package com.driverhub;
import java.time.LocalDateTime;

import jakarta.persistence.*;
@Entity

public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Passenger passenger;

    @ManyToOne
    private Driver driver; // Null until a driver accepts

    private String pickupLocation;
    private String dropLocation;
    
    private Double startLat;
    private Double startLng;
    private Double endLat;
    private Double endLng;

    private Double totalKm;
    private Double finalFare;
    
 // Add these fields to your existing Trip entity
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double startOdometer; // Optional: for physical meter reading

    // Status: POSTED, ACCEPTED, STARTED, COMPLETED
    private String status = "POSTED";
    private LocalDateTime postedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public String getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}
	public LocalDateTime getPostedTime() { return postedTime; }
    public void setPostedTime(LocalDateTime postedTime) { this.postedTime = postedTime; }


	public String getDropLocation() {
		return dropLocation;
	}

	public void setDropLocation(String dropLocation) {
		this.dropLocation = dropLocation;
	}

	public Double getStartLat() {
		return startLat;
	}

	public void setStartLat(Double startLat) {
		this.startLat = startLat;
	}

	public Double getStartLng() {
		return startLng;
	}

	public void setStartLng(Double startLng) {
		this.startLng = startLng;
	}

	public Double getEndLat() {
		return endLat;
	}

	public void setEndLat(Double endLat) {
		this.endLat = endLat;
	}

	public Double getEndLng() {
		return endLng;
	}

	public void setEndLng(Double endLng) {
		this.endLng = endLng;
	}

	public Double getTotalKm() {
		return totalKm;
	}

	public void setTotalKm(Double totalKm) {
		this.totalKm = totalKm;
	}

	public Double getFinalFare() {
		return finalFare;
	}

	public void setFinalFare(Double finalFare) {
		this.finalFare = finalFare;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Double getStartOdometer() {
		return startOdometer;
	}

	public void setStartOdometer(Double startOdometer) {
		this.startOdometer = startOdometer;
	} 
    
    
    
}