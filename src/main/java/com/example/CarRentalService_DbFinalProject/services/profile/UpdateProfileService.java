// File: src/main/java/com/example/CarRentalService_DbFinalProject/services/profile/UpdateProfileService.java
package com.example.CarRentalService_DbFinalProject.services.profile;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateProfileService {

    private final UserRepository userRepository;

    public UpdateProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Merge the form's fullName/email into the existing user and save.
     */
    public Users execute(String username, Users formData) {
        Users user = userRepository
            .findByUserName(username)
            .orElseThrow(() -> new RuntimeException("User not found: " + username));

        user.setFullName(formData.getFullName());
        user.setEmail(formData.getEmail());
        // (add any other editable fields here)

        return userRepository.save(user);
    }
}
