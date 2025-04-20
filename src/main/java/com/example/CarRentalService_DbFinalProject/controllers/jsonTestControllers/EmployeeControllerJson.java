package com.example.CarRentalService_DbFinalProject.controllers.jsonTestControllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.services.employee.AddVehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/json/employee")
public class EmployeeControllerJson {

    private final AddVehicleService addVehicleService;

    public EmployeeControllerJson(AddVehicleService addVehicleService) {
        this.addVehicleService = addVehicleService;
    }

    @PostMapping("/vehicles")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> addVehicle (@RequestBody Vehicle vehicle) {
        return addVehicleService.execute(vehicle);
    }

}
