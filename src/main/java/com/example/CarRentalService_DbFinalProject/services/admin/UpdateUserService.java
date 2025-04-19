package com.example.CarRentalService_DbFinalProject.services.admin;

import com.example.CarRentalService_DbFinalProject.errorHandling.validations.UserValidation;
import com.example.CarRentalService_DbFinalProject.errorHandling.validations.Username_Email_Availability;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class UpdateUserService {

    private final DataSource dataSource;
    private final Username_Email_Availability username_email_availability;

    public UpdateUserService(DataSource dataSource, Username_Email_Availability usernameEmailAvailability) {
        this.dataSource = dataSource;
        username_email_availability = usernameEmailAvailability;
    }

    public ResponseEntity<String> execute(Users updatedUser, int userId) {

        //checks if the email/username is taken
        username_email_availability.execute(updatedUser);

        // Validate incoming data
        UserValidation.execute(updatedUser);

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

            stmt.setString(1, updatedUser.getFullName());
            stmt.setString(2, updatedUser.getEmail());
            stmt.setString(3, updatedUser.getRole().name());
            stmt.setString(4, updatedUser.getUserName());
            stmt.setInt(5, userId);

           stmt.executeUpdate();

            return ResponseEntity.ok("User updated successfully");

        } catch (SQLException e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }
}
