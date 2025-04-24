package com.example.CarRentalService_DbFinalProject.services.customer;

import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.model.repositories.VehicleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class GetVehicleViaIdServiceForCustomer {

    private final VehicleRepository vehicleRepository;

    public GetVehicleViaIdServiceForCustomer(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public ResponseEntity<Vehicle> execute(int id) {

        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);

        if (optionalVehicle.isPresent()) {
            return ResponseEntity.ok(optionalVehicle.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found with id: " + id);
        }

    }

}
