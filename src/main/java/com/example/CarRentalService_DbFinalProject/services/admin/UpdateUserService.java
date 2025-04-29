package com.example.CarRentalService_DbFinalProject.services.admin;

import com.example.CarRentalService_DbFinalProject.errorHandling.validations.UserValidation;
import com.example.CarRentalService_DbFinalProject.errorHandling.validations.Username_Email_Availability;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class UpdateUserService {

    private final DataSource dataSource;
    private final Username_Email_Availability usernameEmailAvailability;
    private final UserRepository userRepository;

    public UpdateUserService(
            DataSource dataSource,
            Username_Email_Availability usernameEmailAvailability,
            UserRepository userRepository
    ) {
        this.dataSource = dataSource;
        this.usernameEmailAvailability = usernameEmailAvailability;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> execute(Users updatedUser, int userId) {
        Users existing = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!updatedUser.getUserName().equals(existing.getUserName())) {
            usernameEmailAvailability.execute(updatedUser);
            existing.setUserName(updatedUser.getUserName());
        }

        if (!updatedUser.getEmail().equals(existing.getEmail())) {
            usernameEmailAvailability.execute(updatedUser);
            existing.setEmail(updatedUser.getEmail());
        }

        existing.setFullName(updatedUser.getFullName());

        existing.setRole(updatedUser.getRole());

        UserValidation.execute(existing);

        String sql = """
            UPDATE users
               SET full_name = ?,
                   email     = ?,
                   role      = ?,
                   user_name = ?
             WHERE user_id  = ?
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, existing.getFullName());
            stmt.setString(2, existing.getEmail());
            stmt.setString(3, existing.getRole().name());
            stmt.setString(4, existing.getUserName());
            stmt.setInt(5, userId);

            stmt.executeUpdate();
            return ResponseEntity.ok("User updated successfully");
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }
}
