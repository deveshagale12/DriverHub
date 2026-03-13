
package com.example.Private_CAR_Booking;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// This annotation tells Spring to return a 404 status if this is thrown
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PassengerNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public PassengerNotFoundException(String message) {
        super(message);
    }
}