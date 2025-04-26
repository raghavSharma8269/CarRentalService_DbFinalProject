package com.example.CarRentalService_DbFinalProject.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/admin")
public class AdminController {


    // Account Management Dashboard Page
    @GetMapping("/accounts")
    @PreAuthorize("hasRole('ADMIN')")
    public String accounts(Model model) {
        model.addAttribute("page", "accounts");
        return "/pages/user-dash";
    }

}
