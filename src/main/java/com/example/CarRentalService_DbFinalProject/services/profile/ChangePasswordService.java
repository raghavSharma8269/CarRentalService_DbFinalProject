package com.example.CarRentalService_DbFinalProject.services.profile;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import com.example.CarRentalService_DbFinalProject.security.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

@Service
public class ChangePasswordService {

    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;

    public ChangePasswordService(AuthUtil authUtil, UserRepository userRepository,
                                 PasswordEncoder passwordEncoder, DataSource dataSource) {
        this.authUtil = authUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
    }

    public ResponseEntity<String> execute(String currentPassword, String newPassword, String confirmNewPassword) {
        String username = authUtil.getLoggedInUsername();

        Optional<Users> optionalUser = userRepository.findByUserName(username);
        Users user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));


        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return ResponseEntity.badRequest().body("Current password is incorrect");
        }

        if (!passwordEncoder.matches(newPassword, user.getPassword())) {
            return ResponseEntity.badRequest().body("New password cannot be the same as current password");
        }

        if (!newPassword.equals(confirmNewPassword)) {
            return ResponseEntity.badRequest().body("New passwords do not match");
        }


        String sql = "UPDATE users SET password = ? WHERE user_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, passwordEncoder.encode(newPassword));
            stmt.setInt(2, user.getUserId());

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error updating password: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Password update failed");
        }

        return ResponseEntity.ok("Password changed successfully");
    }
}
