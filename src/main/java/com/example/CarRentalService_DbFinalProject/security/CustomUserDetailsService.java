package com.example.CarRentalService_DbFinalProject.security;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository
                .findByUserName(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + username)
                );

        return User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
