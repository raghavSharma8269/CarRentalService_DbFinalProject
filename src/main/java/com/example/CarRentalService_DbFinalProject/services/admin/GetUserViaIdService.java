package com.example.CarRentalService_DbFinalProject.services.admin;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetUserViaIdService {

    private final UserRepository userRepository;

    public GetUserViaIdService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Users> execute(int userId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(user);

    }

}
