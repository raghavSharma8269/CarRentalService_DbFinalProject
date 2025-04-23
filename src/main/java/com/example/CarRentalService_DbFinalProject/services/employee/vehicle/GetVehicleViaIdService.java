package com.example.CarRentalService_DbFinalProject.services.employee.vehicle;

import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.model.repositories.VehicleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetVehicleViaIdService {

    private final VehicleRepository vehicleRepository;

    public GetVehicleViaIdService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public ResponseEntity<Vehicle> execute(int vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        return ResponseEntity.ok(vehicle);
    }

}
