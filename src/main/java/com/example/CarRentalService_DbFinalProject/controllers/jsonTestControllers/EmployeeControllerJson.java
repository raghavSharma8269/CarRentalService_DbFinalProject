package com.example.CarRentalService_DbFinalProject.controllers.jsonTestControllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.services.employee.AddVehicleService;
import com.example.CarRentalService_DbFinalProject.services.employee.DeleteVehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/json/employee")
public class EmployeeControllerJson {

    private final AddVehicleService addVehicleService;
    private final DeleteVehicleService deleteVehicleService;

    public EmployeeControllerJson(
            AddVehicleService addVehicleService,
            DeleteVehicleService deleteVehicleService
    ) {
        this.addVehicleService = addVehicleService;
        this.deleteVehicleService = deleteVehicleService;
    }

    @PostMapping("/vehicles")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> addVehicle (@RequestBody Vehicle vehicle) {
        return addVehicleService.execute(vehicle);
    }

    @DeleteMapping("/vehicles/{vehicleId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteVehicle(@PathVariable int vehicleId) {
        deleteVehicleService.execute(vehicleId);
        return ResponseEntity.ok("Vehicle deleted successfully");
    }

}
