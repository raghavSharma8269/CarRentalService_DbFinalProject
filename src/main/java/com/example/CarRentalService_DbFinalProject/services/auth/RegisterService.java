package com.example.CarRentalService_DbFinalProject.services.auth;

import com.example.CarRentalService_DbFinalProject.errorHandling.validations.UserValidation;
import com.example.CarRentalService_DbFinalProject.errorHandling.validations.Username_Email_Availability;
import com.example.CarRentalService_DbFinalProject.model.Roles;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Username_Email_Availability username_email_availability;

    public RegisterService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            Username_Email_Availability usernameEmailAvailability
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        username_email_availability = usernameEmailAvailability;
    }

    public ResponseEntity<String> execute(@RequestBody Users user) {

            // Check if the email/username is taken
            username_email_availability.execute(user);

            // Validate the new user
            UserValidation.execute(user);

            Users newUser = new Users(
                    0,
                    user.getUserName(),
                    passwordEncoder.encode(user.getPassword()),
                    user.getEmail(),
                    user.getFullName(),
                    Roles.CUSTOMER
            );


           userRepository.save(newUser);
           return ResponseEntity.ok().body("Registered");


}
}
