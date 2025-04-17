package com.example.CarRentalService_DbFinalProject.controllers.jsonTestControllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import com.example.CarRentalService_DbFinalProject.services.auth.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/json/auth")
public class AuthControllerJson {

    private final RegisterService registerService;
    private final UserRepository userRepository;

    public AuthControllerJson(UserRepository userRepository, RegisterService registerService) {
        this.userRepository = userRepository;
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerViaJson(@RequestBody Users user) {
       return registerService.execute(user);
    }


}
