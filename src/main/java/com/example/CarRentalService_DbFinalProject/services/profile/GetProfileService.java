package com.example.CarRentalService_DbFinalProject.services.profile;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import com.example.CarRentalService_DbFinalProject.security.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetProfileService {

    private final AuthUtil authUtil;
    private final UserRepository userRepository;

    public GetProfileService(AuthUtil authUtil, UserRepository userRepository) {
        this.authUtil = authUtil;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Users> execute(){
        String username = authUtil.getLoggedInUsername();
        Optional <Users> optionalUser = userRepository.findByUserName(username);

        Users user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(user);
    }

}
