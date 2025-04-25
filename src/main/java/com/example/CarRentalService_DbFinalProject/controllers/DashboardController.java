package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.model.repositories.VehicleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final VehicleRepository vehicleRepository;

    public DashboardController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    // Default Dashboard Page
    @GetMapping()
    public String dashboard(Model model) {
        model.addAttribute("page", "dashboard");
        return "pages/employee-admin-dash";
    }

    // Reservation Dashboard Page
    @GetMapping("/reservations")
    public String reservations(Model model) {
        model.addAttribute("page", "reservations");
        return "pages/employee-admin-dash";
    }

    // Vehicles Dashboard Page (Vehicle.HTML page will be embedded w/Manage button)
    @GetMapping("/vehicles")
    public String vehicles(Model model) {
        model.addAttribute("page", "vehicles");
        return "pages/employee-admin-dash";
    }

    // Load details page when you click into a car
    @GetMapping("/vehicle/{id}")
    public String showVehicleDetails(@PathVariable int id, Model model) {
        // Check for the vehicle in the database
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);

        // Set the page attribute to vehicleDetails for rendering
        model.addAttribute("page", "vehicleDetails");

        // If the vehicle is found, add it to the model otherwise add an error message
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
        } else {
            model.addAttribute("message", "Vehicle not found.");
        }

        // Return the vehicle-card.html fragment to be rendered
        return "pages/employee-admin-dash";
    }

    // Load the checkout page when you click on a 'Rent Now' button
    @GetMapping("/checkout/{id}")
    public String showCheckout(@PathVariable int id, Model model) {

        // Check for the vehicle in the database
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);

        // Set the page attribute to vehicleDetails for rendering
        model.addAttribute("page", "vehicleCheckout");

        // If the vehicle is found, add it to the model otherwise add an error message
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
            System.out.println("Vehicle ID: " + id + " | Model: " + model);
            return "/pages/employee-admin-dash";
        } else {
            model.addAttribute("message", "Vehicle not found.");
            return "redirect:/api/dashboard/vehicles"; // Redirect to the vehicle page if not found
        }
    }

    // Maintenance Dashboard Page
    @GetMapping("/maintenance")
    public String maintenance(Model model) {
        model.addAttribute("page", "maintenance");
        return "pages/employee-admin-dash";
    }

    // Coupons Dashboard Page
    @GetMapping("/coupons")
    public String coupons(Model model) {
        model.addAttribute("page", "coupons");
        return "pages/employee-admin-dash";
    }

    // Account Management Dashboard Page
    @GetMapping("/accounts")
    // @PreAuthorize("hasRole('ADMIN')")
    public String accounts(Model model) {
        model.addAttribute("page", "accounts");
        return "pages/employee-admin-dash";
    }

}