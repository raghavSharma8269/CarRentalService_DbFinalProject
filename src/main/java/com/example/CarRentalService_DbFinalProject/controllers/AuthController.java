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

    public AuthController(RegisterService registerService) {
        this.registerService = registerService;
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

}

