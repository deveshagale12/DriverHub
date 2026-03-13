
package com.example.Private_CAR_Booking;
import java.time.LocalDateTime;

public class ErrorDetails {
    private LocalDateTime timestamp;
    private int status;         // Added status code (e.g., 404, 401)
    private String message;
    private String details;

    public ErrorDetails(LocalDateTime timestamp, int status, String message, String details) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.details = details;
    }

    // Getters
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public String getDetails() { return details; }

    // Setters
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setStatus(int status) { this.status = status; }
    public void setMessage(String message) { this.message = message; }
    public void setDetails(String details) { this.details = details; }
}