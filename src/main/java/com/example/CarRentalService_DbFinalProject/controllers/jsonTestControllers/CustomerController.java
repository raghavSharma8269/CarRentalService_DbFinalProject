package com.example.CarRentalService_DbFinalProject.controllers.jsonTestControllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.services.customer.GetAllAvailableVehiclesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/json/customer")
public class CustomerController {

    private final GetAllAvailableVehiclesService getAllAvailableVehiclesService;

    public CustomerController(GetAllAvailableVehiclesService getAllAvailableVehiclesService) {
        this.getAllAvailableVehiclesService = getAllAvailableVehiclesService;
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

}
