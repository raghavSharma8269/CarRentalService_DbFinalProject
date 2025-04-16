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

        // Check if the email is taken
        if (optionalUserViaEmail.isPresent()) {
            throw new RegisterException("Email is already taken");
        }
        // Check if the username is taken
        if (optionalUserViaUserName.isPresent()) {
            throw new RegisterException("Username is already taken");
        }
        // If both are empty, the email and username are available
    }

}
