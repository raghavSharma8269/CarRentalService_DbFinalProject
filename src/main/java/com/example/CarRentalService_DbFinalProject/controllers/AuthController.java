package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.services.auth.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegisterService registerService;

    public AuthController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user) {
        registerService.execute(user);
        return ResponseEntity.ok("Registered");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("admin");
    }

    @GetMapping("/employee")
    @PreAuthorize("hasRole('ADMIN' or hasRole('EMPLOYEE'))")
    public ResponseEntity<String> employee() {
        return ResponseEntity.ok("employee");
    }


}
