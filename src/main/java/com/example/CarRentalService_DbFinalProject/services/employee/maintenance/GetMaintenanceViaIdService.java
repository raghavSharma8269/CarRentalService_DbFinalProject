package com.example.CarRentalService_DbFinalProject.services.employee.maintenance;

import com.example.CarRentalService_DbFinalProject.model.entities.Maintenance;
import com.example.CarRentalService_DbFinalProject.model.repositories.MaintenanceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetMaintenanceViaIdService {

    private final MaintenanceRepository maintenanceRepository;

    public GetMaintenanceViaIdService(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    public ResponseEntity<Maintenance> execute(int id) {

        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found"));

        return ResponseEntity.ok(maintenance);

    }
}
