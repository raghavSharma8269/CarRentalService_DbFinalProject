package com.example.CarRentalService_DbFinalProject.services.employee.vehicle;

import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.model.repositories.VehicleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllVehicles {

    private final VehicleRepository vehicleRepository;

    public GetAllVehicles(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public ResponseEntity<List<Vehicle>> execute(String keyword, Double minPrice, Double maxPrice) {
        List<Vehicle> vehicles = vehicleRepository.searchByKeywordAndPrice(keyword,minPrice,maxPrice );
        return ResponseEntity.ok(vehicles);
    }

}
