package com.example.CarRentalService_DbFinalProject.controllers.jsonTestControllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.services.customer.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/json/customer")
public class CustomerController {

    private final GetAllAvailableVehiclesService getAllAvailableVehiclesService;
    private final GetVehicleViaIdService getVehicleViaIdService;
    private final CreateReservationService createReservationService;
    private final GetReservationsService getReservationsService;

    public CustomerController(
            GetAllAvailableVehiclesService getAllAvailableVehiclesService,
            GetVehicleViaIdService getVehicleViaIdService,
            CreateReservationService createReservationService, GetReservationsService getReservationsService
    ) {
        this.getAllAvailableVehiclesService = getAllAvailableVehiclesService;
        this.getVehicleViaIdService = getVehicleViaIdService;
        this.createReservationService = createReservationService;
        this.getReservationsService = getReservationsService;
    }

    //Vehicle Requests for Customer

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

    //Reservation Requests for Customer
    @PostMapping("/reservation")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> createReservation(@RequestBody Reservation reservation) {
        return createReservationService.execute(reservation);
    }

    @GetMapping("/reservation")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<List<Reservation>> getReservations(@RequestParam(required = false) String reservationId) {
        return getReservationsService.execute(reservationId);
    }


}
