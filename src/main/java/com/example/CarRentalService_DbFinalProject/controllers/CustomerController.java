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
@RequestMapping("/api/customer")
public class CustomerController {

    private final VehicleRepository vehicleRepository;

    public CustomerController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    // ✅ Loads the vehicle list page
    @GetMapping("/vehicle")
    public String showVehiclePage() {
        return "index";
    }

    // ✅ Loads the details page when you click on a car
    @GetMapping("/vehicle/{id}")
    public String showVehicleDetails(@PathVariable int id, Model model) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
            return "vehicle"; // this renders vehicle.html
        } else {
            model.addAttribute("message", "Vehicle not found.");
            return "vehicle"; // still show the page with a message
        }
    }

    @GetMapping("/checkout/{id}")
    public String showCheckout(@PathVariable int id, Model model) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
            return "checkout";
        } else {
            return "redirect:/vehicle";
        }
    }

}
