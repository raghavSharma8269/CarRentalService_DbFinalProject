package com.example.CarRentalService_DbFinalProject.services.admin;

import com.example.CarRentalService_DbFinalProject.errorHandling.validations.UserValidation;
import com.example.CarRentalService_DbFinalProject.errorHandling.validations.Username_Email_Availability;
import com.example.CarRentalService_DbFinalProject.model.Roles;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Component
public class AddEmployeeService {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final Username_Email_Availability username_email_availability;

    public AddEmployeeService(
            DataSource dataSource, PasswordEncoder passwordEncoder,
            Username_Email_Availability usernameEmailAvailability
    ) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        username_email_availability = usernameEmailAvailability;
    }

    public ResponseEntity<String> execute (Users user) {

            // Check if the email/username is taken
            username_email_availability.execute(user);

            // Validate the new user
            UserValidation.execute(user);

            String sql = "INSERT INTO users (user_name, password, email, full_name, role) VALUES (?, ?, ?, ?, ?)";


            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, user.getUserName());
                stmt.setString(2, passwordEncoder.encode(user.getPassword()));
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getFullName());
                stmt.setString(5, user.getRole().toString());

                stmt.executeUpdate();

            } catch (Exception e) {
                throw new RuntimeException("Error adding employee: " + e.getMessage(), e);
            }

        return ResponseEntity.ok("Employee added successfully");
    }

}
