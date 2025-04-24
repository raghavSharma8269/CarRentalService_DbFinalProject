package com.example.CarRentalService_DbFinalProject.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/dashboard")
public class DashboardController {

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