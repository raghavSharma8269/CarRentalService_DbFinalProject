package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final com.example.CarRentalService_DbFinalProject.sql.AddEmployeeService addEmployeeService;

    public AdminController(com.example.CarRentalService_DbFinalProject.sql.AddEmployeeService addEmployeeService) {
        this.addEmployeeService = addEmployeeService;
    }

    @PostMapping("/add-employee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addEmployee(@RequestBody Users user) {
        addEmployeeService.execute(user);
        return ResponseEntity.ok("Employee added successfully");
    }

}
