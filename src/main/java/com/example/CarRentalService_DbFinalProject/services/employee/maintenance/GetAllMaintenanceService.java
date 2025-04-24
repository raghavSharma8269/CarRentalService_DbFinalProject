package com.example.CarRentalService_DbFinalProject.services.employee.maintenance;

import com.example.CarRentalService_DbFinalProject.model.entities.Maintenance;
import com.example.CarRentalService_DbFinalProject.model.repositories.MaintenanceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllMaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    public GetAllMaintenanceService(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    public ResponseEntity<List<Maintenance>> execute(String keyword) {

        List<Maintenance> maintenanceList = maintenanceRepository.searchByKeywordAndPrice(keyword);

        return ResponseEntity.ok(maintenanceList);

    }
}