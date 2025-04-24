package com.example.CarRentalService_DbFinalProject.services.employee.maintenance;

import com.example.CarRentalService_DbFinalProject.model.entities.Maintenance;
import com.example.CarRentalService_DbFinalProject.model.repositories.MaintenanceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GetMaintenanceViaIdService {

    private final MaintenanceRepository maintenanceRepository;

    public GetMaintenanceViaIdService(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    public ResponseEntity<Maintenance> execute(int maintenanceId) {

        Maintenance maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Maintenance not found with id: " + maintenanceId));

        return ResponseEntity.ok(maintenance);

    }
}
