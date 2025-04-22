package com.example.CarRentalService_DbFinalProject.controllers.jsonTestControllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.services.customer.GetAllAvailableVehiclesService;
import com.example.CarRentalService_DbFinalProject.services.customer.GetVehicleViaIdService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/json/customer")
public class CustomerController {

    private final GetAllAvailableVehiclesService getAllAvailableVehiclesService;
    private final GetVehicleViaIdService getVehicleViaIdService;

    public CustomerController(GetAllAvailableVehiclesService getAllAvailableVehiclesService, GetVehicleViaIdService getVehicleViaIdService) {
        this.getAllAvailableVehiclesService = getAllAvailableVehiclesService;
        this.getVehicleViaIdService = getVehicleViaIdService;
    }

    @GetMapping("/vehicles")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<List<Vehicle>> getAllAvailableVehicles(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice

    ) {
        return getAllAvailableVehiclesService.execute(keyword, minPrice, maxPrice);
    }

    @GetMapping("/vehicles/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable int id) {
        return getVehicleViaIdService.execute(id);
    }

}
