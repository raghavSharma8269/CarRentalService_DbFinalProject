package com.example.CarRentalService_DbFinalProject.errorHandling.validations;

import com.example.CarRentalService_DbFinalProject.errorHandling.Exceptions.RegisterException;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;

public class UserValidation {

    public static void execute(Users user) {
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            throw new RegisterException("Username cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RegisterException("Password cannot be null or empty");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new RegisterException("Email cannot be null or empty");
        }
        if (user.getFullName() == null || user.getFullName().isEmpty()) {
            throw new RegisterException("Full name cannot be null or empty");
        }
        if(user.getUserName().length() < 3) {
            throw new RegisterException("Username must be at least 3 characters long");
        }
        if(user.getRole() == null){
            throw new RegisterException("Role cannot be null");
        }
    }

}
