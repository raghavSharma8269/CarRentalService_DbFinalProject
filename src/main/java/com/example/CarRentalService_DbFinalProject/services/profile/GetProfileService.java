// File: src/main/java/com/example/CarRentalService_DbFinalProject/services/profile/GetProfileService.java
package com.example.CarRentalService_DbFinalProject.services.profile;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetProfileService {

    private final UserRepository userRepository;

    public GetProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users execute(String username){
        return userRepository
                .findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}
