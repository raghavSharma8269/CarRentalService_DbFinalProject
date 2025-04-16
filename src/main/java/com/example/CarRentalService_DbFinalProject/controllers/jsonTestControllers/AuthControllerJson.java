package com.example.CarRentalService_DbFinalProject.controllers.jsonTestControllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.services.auth.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/json/auth")
public class AuthControllerJson {

    private final RegisterService registerService;

    public AuthControllerJson(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerViaJson(@RequestBody Users user) {
       return registerService.execute(user);
    }
}
