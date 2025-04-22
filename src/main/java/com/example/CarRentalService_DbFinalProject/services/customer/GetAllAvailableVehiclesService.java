package com.example.CarRentalService_DbFinalProject.services.customer;

import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.model.repositories.VehicleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllAvailableVehiclesService {

    private final VehicleRepository vehicleRepository;

    public GetAllAvailableVehiclesService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public ResponseEntity<List<Vehicle>> execute(String keyword, Double minPrice, Double maxPrice) {

        List<Vehicle> availableVehicles = vehicleRepository.searchAvailableByKeywordAndPrice(keyword, minPrice, maxPrice);
        return ResponseEntity.ok(availableVehicles);

    }

}
