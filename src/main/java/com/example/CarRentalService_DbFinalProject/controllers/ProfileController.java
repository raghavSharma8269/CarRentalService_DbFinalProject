// File: src/main/java/com/example/CarRentalService_DbFinalProject/controllers/ProfileController.java
package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.services.profile.GetProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private final GetProfileService getProfileService;

    public ProfileController(GetProfileService getProfileService) {
        this.getProfileService = getProfileService;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Users user = getProfileService.execute().getBody();
        model.addAttribute("user", user);
        return "fragments/dashboard/profile";  // your profile.html under templates/pages
    }
}
