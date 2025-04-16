package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.services.auth.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private final RegisterService registerService;

    public AuthController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new Users());
        return "register"; // returns register.html
    }

    @PostMapping("/register")
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
