package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.model.repositories.VehicleRepository;
import com.example.CarRentalService_DbFinalProject.services.customer.GetAllAvailableVehiclesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/customer")
public class CustomerController {

    private final VehicleRepository vehicleRepository;

    private final GetAllAvailableVehiclesService getAllAvailableVehiclesService;

    public CustomerController(VehicleRepository vehicleRepository, GetAllAvailableVehiclesService getAllAvailableVehiclesService) {
        this.vehicleRepository = vehicleRepository;
        this.getAllAvailableVehiclesService = getAllAvailableVehiclesService;
    }


    // Reservation Dashboard Page
    @GetMapping("/reservations")
    public String reservations(Model model) {
        model.addAttribute("page", "reservations");
        return "/pages/user-dash";
    }

    // Vehicles Dashboard Page (Vehicle.HTML page will be embedded w/Manage button)
    @GetMapping("/vehicles")
    public String listVehicles(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model
    ) {
        // fetch all vehicles (regardless of availability)
        List<Vehicle> vehicles = getAllAvailableVehiclesService
                .execute(keyword, minPrice, maxPrice)
                .getBody();

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("page", "vehicles");
        return "pages/user-dash";
    }


    // Load details page when you click into a car
    @GetMapping("/vehicles/{id}")
    public String showVehicleDetails(@PathVariable int id, Model model) {
        // Check for the vehicle in the database
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);

        model.addAttribute("page", "vehicleDetails");

        // If the vehicle is found, add it to the model otherwise add an error message
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
        } else {
            model.addAttribute("message", "Vehicle not found.");
        }

        return "/pages/user-dash";
    }

    // Load the checkout page when you click on a 'Rent Now' button
    @GetMapping("/checkout/{id}")
    public String showCheckout(@PathVariable int id, Model model) {

        // Check for the vehicle in the database
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);

        model.addAttribute("page", "vehicleCheckout");

        // If the vehicle is found, add it to the model otherwise add an error message
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
            return "/pages/user-dash";
        } else {
            model.addAttribute("message", "Vehicle not found.");
            return "redirect:/api/dashboard/vehicles"; // Redirect to the vehicle page if not found
        }
    }
}
