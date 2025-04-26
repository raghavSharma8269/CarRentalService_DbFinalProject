// File: src/main/java/com/example/CarRentalService_DbFinalProject/controllers/ProfileController.java
package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import com.example.CarRentalService_DbFinalProject.security.AuthUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    public ProfileController(UserRepository userRepository, AuthUtil authUtil) {
        this.userRepository = userRepository;
        this.authUtil = authUtil;
    }

    @GetMapping
    public String profile(Model model) {
        // grab the username from Basic Auth
        String username = authUtil.getLoggedInUsername();

        // lookup the full Users entity
        Users user = userRepository
            .findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        model.addAttribute("user", user);
        return "fragments/dashboard/profile";
    }
}
