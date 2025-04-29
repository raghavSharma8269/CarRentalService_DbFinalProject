// File: src/main/java/com/example/CarRentalService_DbFinalProject/services/profile/UpdateProfileService.java
package com.example.CarRentalService_DbFinalProject.services.profile;

import com.example.CarRentalService_DbFinalProject.errorHandling.validations.Username_Email_Availability;
import com.example.CarRentalService_DbFinalProject.errorHandling.validations.UserValidation;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateProfileService {

    private final UserRepository userRepository;
    private final Username_Email_Availability usernameEmailAvailability;

    public UpdateProfileService(
            UserRepository userRepository,
            Username_Email_Availability usernameEmailAvailability
    ) {
        this.userRepository = userRepository;
        this.usernameEmailAvailability = usernameEmailAvailability;
    }

    @Transactional
    public Users execute(String username, Users formData) {
        Users existing = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

         // only check & update email if changed
        if (!formData.getEmail().equals(existing.getEmail())) {
            usernameEmailAvailability.execute(formData);
            existing.setEmail(formData.getEmail());
        }

        existing.setFullName(formData.getFullName());

        UserValidation.execute(existing);

        return userRepository.save(existing);
    }
}
