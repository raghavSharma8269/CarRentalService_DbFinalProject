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

    public List<Users> execute(String role, String keyword) {
        Roles enumRole = null;

        if (role != null && !role.isEmpty()) {
            try {
                enumRole = Roles.valueOf(role.toUpperCase());
            } catch (IllegalArgumentException e) {
                return List.of(); // or handle as a 400 error
            }
        }

        return userRepository.findByRoleAndKeyword(enumRole, keyword);
    }

}
