package com.example.CarRentalService_DbFinalProject.errorHandling.validations;

import com.example.CarRentalService_DbFinalProject.errorHandling.Exceptions.RegisterException;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class Username_Email_Availability {

    private final UserRepository userRepository;

    public Username_Email_Availability(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(Users user) {
        Optional<Users> optionalUserViaUserName = userRepository.findByUserName(user.getUserName());
        Optional<Users> optionalUserViaEmail = userRepository.findByEmail(user.getEmail());

        // Check if the email/username is taken
        if (optionalUserViaEmail.isEmpty() && optionalUserViaUserName.isEmpty()) {
            // Email and username are available
            return;
        } else {
            // Email or username is already taken
            throw new RegisterException("Email or username already exists");
        }
    }

}
