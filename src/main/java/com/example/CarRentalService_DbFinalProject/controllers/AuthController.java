package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.model.repositories.VehicleRepository;
import com.example.CarRentalService_DbFinalProject.services.auth.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    private final RegisterService registerService;
    private final VehicleRepository vehicleRepository;

    public AuthController(RegisterService registerService, VehicleRepository vehicleRepository) {
        this.registerService = registerService;
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/api/auth/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/api/auth/register")
    public String register(@ModelAttribute("user") Users user, Model model) {
        try {
            registerService.execute(user);
            model.addAttribute("message", "Registration successful!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        return "register";
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

