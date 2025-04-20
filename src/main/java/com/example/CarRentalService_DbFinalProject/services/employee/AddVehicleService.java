package com.example.CarRentalService_DbFinalProject.services.employee;

import com.example.CarRentalService_DbFinalProject.errorHandling.validations.VehicleValidation;
import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.model.repositories.VehicleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddVehicleService {

    private final VehicleRepository vehicleRepository;

    public AddVehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public ResponseEntity<String> execute(Vehicle vehicle) {
        VehicleValidation.execute(vehicle);
        vehicleRepository.save(vehicle);
        return ResponseEntity.ok("Vehicle added successfully");

    }

}
