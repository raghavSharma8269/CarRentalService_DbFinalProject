package com.example.CarRentalService_DbFinalProject.sql;

import com.example.CarRentalService_DbFinalProject.model.Roles;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Component
public class AddEmployeeService {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    public AddEmployeeService(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute (Users user) {
        String sql = "INSERT INTO users (user_name, password, email, full_name, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, passwordEncoder.encode(user.getPassword()));
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFullName());
            stmt.setString(5, Roles.EMPLOYEE.name());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error inserting employee: " + e.getMessage());
        }
    }

}
