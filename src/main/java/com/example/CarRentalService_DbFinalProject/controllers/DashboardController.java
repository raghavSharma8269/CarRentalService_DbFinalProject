package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.services.profile.GetProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final GetProfileService getProfileService;

    public DashboardController(GetProfileService getProfileService) {
        this.getProfileService = getProfileService;
    }

    @GetMapping
    public String dashboard(Principal principal, Model model) {
        // load the currently-logged-in user
        Users user = getProfileService.execute(principal.getName());
        model.addAttribute("user", user);

        // tell Thymeleaf to render the "dashboard" fragment
        model.addAttribute("page", "dashboard");
        return "pages/user-dash";
    }
}
