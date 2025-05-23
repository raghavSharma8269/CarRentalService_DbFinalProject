package com.example.CarRentalService_DbFinalProject.errorHandling.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegisterException extends RuntimeException {
    public RegisterException(String message) {
        super(message);
    }
}
