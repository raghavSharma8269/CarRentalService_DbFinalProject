package com.example.CarRentalService_DbFinalProject.services.auth;

import com.example.CarRentalService_DbFinalProject.errorHandling.Exceptions.RegisterException;
import com.example.CarRentalService_DbFinalProject.model.Roles;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository,
                           PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> execute(@RequestBody Users user) {

        Optional<Users> optionalUserViaUserName = userRepository.findByUserName(user.getUserName());
        Optional<Users> optionalUserViaEmail = userRepository.findByEmail(user.getEmail());
        if (optionalUserViaEmail.isEmpty() && optionalUserViaUserName.isEmpty()) {

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

        throw new RegisterException("Email or username already exists");
    }

}
