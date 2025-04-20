package com.example.CarRentalService_DbFinalProject.controllers.jsonTestControllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.services.employee.AddVehicleService;
import com.example.CarRentalService_DbFinalProject.services.employee.DeleteVehicleService;
import com.example.CarRentalService_DbFinalProject.services.employee.GetAllVehicles;
import com.example.CarRentalService_DbFinalProject.services.employee.UpdateVehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/json/employee")
public class EmployeeControllerJson {

    private final AddVehicleService addVehicleService;
    private final DeleteVehicleService deleteVehicleService;
    private final UpdateVehicleService updateVehicleService;
    private final GetAllVehicles getAllVehicles;

    public EmployeeControllerJson(
            AddVehicleService addVehicleService,
            DeleteVehicleService deleteVehicleService,
            UpdateVehicleService updateVehicleService,
            GetAllVehicles getAllVehicles
    ) {
        this.addVehicleService = addVehicleService;
        this.deleteVehicleService = deleteVehicleService;
        this.updateVehicleService = updateVehicleService;
        this.getAllVehicles = getAllVehicles;
    }

    @PostMapping("/vehicles")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> addVehicle (@RequestBody Vehicle vehicle) {
        return addVehicleService.execute(vehicle);
    }

    @DeleteMapping("/vehicles/{vehicleId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteVehicle(@PathVariable int vehicleId) {
        return deleteVehicleService.execute(vehicleId);
    }

    @PutMapping("/vehicles/{vehicleId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> updateVehicle(@PathVariable int vehicleId, @RequestBody Vehicle updatedVehicle) {
        return updateVehicleService.execute(vehicleId, updatedVehicle);
    }

    @GetMapping("/vehicles")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<List<Vehicle>> getAllVehicles(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return getAllVehicles.execute(keyword, minPrice, maxPrice);
    }

}
