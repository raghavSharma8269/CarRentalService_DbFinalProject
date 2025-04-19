package com.example.CarRentalService_DbFinalProject.services.admin;

import com.example.CarRentalService_DbFinalProject.errorHandling.Exceptions.NotFoundException;
import com.example.CarRentalService_DbFinalProject.errorHandling.validations.UserValidation;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateUserService {

    private final UserRepository userRepository;

    public UpdateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> execute(Users updatedUser, int userId) {

     Optional<Users> optionalUsers = userRepository.findById(userId);
        if (optionalUsers.isPresent()) {
            UserValidation.execute(updatedUser);
            Users existingUser = optionalUsers.get();
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole());
            userRepository.save(existingUser);
            return ResponseEntity.ok("User updated successfully");
        } else {
            throw new NotFoundException("User with id " + userId + " not found");
        }


    }

}
