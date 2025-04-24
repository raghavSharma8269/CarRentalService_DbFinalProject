package com.example.CarRentalService_DbFinalProject.errorHandling.validations;

import com.example.CarRentalService_DbFinalProject.model.entities.Maintenance;

public class MaintenanceValidation {

    public static void validate (Maintenance maintenance) {
        if (maintenance.getStart() == null || maintenance.getEnd() == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (maintenance.getStart().isAfter(maintenance.getEnd())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
       if(maintenance.getDescription() == null || maintenance.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (maintenance.getVehicleId() == null) {
            throw new IllegalArgumentException("Vehicle ID cannot be null");
        }
        if (maintenance.getVehicleId().getVehicleId() <= 0) {
            throw new IllegalArgumentException("Vehicle ID must be a positive integer");
       }
        if (maintenance.getDescription().length() < 5) {
            throw new IllegalArgumentException("Description must be at least 5 characters long");
        }
    }

}
