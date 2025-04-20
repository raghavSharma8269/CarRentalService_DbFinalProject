package com.example.CarRentalService_DbFinalProject.errorHandling.validations;

import com.example.CarRentalService_DbFinalProject.errorHandling.Exceptions.VehicleException;
import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;

public class VehicleValidation {

    public static void execute (Vehicle vehicle) {

        if (vehicle.getModel() == null || vehicle.getModel().isEmpty()) {
            throw new VehicleException("Model cannot be null or empty");
        }
        if (vehicle.getMake() == null || vehicle.getMake().isEmpty()) {
            throw new VehicleException("Make cannot be null or empty");
        }
        if (vehicle.getYear() == null || vehicle.getYear().isEmpty()) {
            throw new VehicleException("Year cannot be null or empty");
        }
        if (vehicle.getLicensePlate() == null || vehicle.getLicensePlate().isEmpty()) {
            throw new VehicleException("License plate cannot be null or empty");
        }
        if (vehicle.getPricePerDay() <= 0) {
            throw new VehicleException("Price per day must be greater than 0");
        }
        if (vehicle.getDescription() == null || vehicle.getDescription().isEmpty()) {
            throw new VehicleException("Description cannot be null or empty");
        }
        if (vehicle.getImageUrl() == null || vehicle.getImageUrl().isEmpty()) {
            throw new VehicleException("Image URL cannot be null or empty");
        }

    }

}
