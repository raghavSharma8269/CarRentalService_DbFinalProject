package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.services.admin.AddEmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AddEmployeeService addEmployeeService;

    public AdminController(AddEmployeeService addEmployeeService) {
        this.addEmployeeService = addEmployeeService;
    }

    @PostMapping("/add-employee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addEmployee(@RequestBody Users user) {
        addEmployeeService.execute(user);
        return ResponseEntity.ok("Employee added successfully");
    }

}
