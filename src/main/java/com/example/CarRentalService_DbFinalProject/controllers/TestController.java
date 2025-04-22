package com.example.CarRentalService_DbFinalProject.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/employee-admin-dash")
    public String employeeAdminDash() {
        return "employee-admin-dash"; // returns employee-admin-dash.html
    }
}
