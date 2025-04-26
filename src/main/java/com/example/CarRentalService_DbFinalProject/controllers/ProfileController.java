// File: src/main/java/com/example/CarRentalService_DbFinalProject/controllers/ProfileController.java
package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.services.profile.GetProfileService;
import com.example.CarRentalService_DbFinalProject.services.profile.UpdateProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
public class ProfileController {

    private final GetProfileService getProfileService;
    private final UpdateProfileService updateProfileService;

    public ProfileController(GetProfileService getProfileService,
                             UpdateProfileService updateProfileService) {
        this.getProfileService = getProfileService;
        this.updateProfileService = updateProfileService;
    }

    @GetMapping("/profile")
    public String profileForm(Principal principal, Model model,
                              @RequestParam(value="success", required=false) Boolean success) {
        String username = principal.getName();
        Users user = getProfileService.execute(username);
        model.addAttribute("user", user);
        if (Boolean.TRUE.equals(success)) {
            model.addAttribute("success", true);
        }
        return "fragments/dashboard/profile";
    }

    @PostMapping("/profile")
    public String saveProfile(Principal principal,
                              @ModelAttribute("user") Users formData,
                              Model model) {
        String username = principal.getName();
        try {
            Users updated = updateProfileService.execute(username, formData);
            // redirect with a success flag so GET can show the message
            return "redirect:/profile?success=true";
        } catch (Exception e) {
            model.addAttribute("error", "Could not update profile: " + e.getMessage());
            model.addAttribute("user", formData);
            return "fragments/dashboard/profile";
        }
    }
}
