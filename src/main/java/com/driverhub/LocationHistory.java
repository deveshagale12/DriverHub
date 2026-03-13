
package com.example.Private_CAR_Booking;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "location_history")
public class LocationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long passengerId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;

    // Standard Constructor
    public LocationHistory(Long passengerId, Double latitude, Double longitude, LocalDateTime timestamp) {
        this.passengerId = passengerId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(Long passengerId) {
		this.passengerId = passengerId;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

    // Standard Getters/Setters (omitted for brevity)
    
    
    
}