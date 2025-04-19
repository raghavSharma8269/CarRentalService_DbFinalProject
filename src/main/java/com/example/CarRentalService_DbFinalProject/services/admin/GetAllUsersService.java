package com.example.CarRentalService_DbFinalProject.services.admin;

import com.example.CarRentalService_DbFinalProject.model.Roles;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllUsersService {

    private final UserRepository userRepository;

    public GetAllUsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> execute(String role) {

        if (role == null || role.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.findByRole(Roles.valueOf(role));
        }
    }



}
