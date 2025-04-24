package com.example.CarRentalService_DbFinalProject.services.employee.vehicle;

import com.example.CarRentalService_DbFinalProject.errorHandling.validations.VehicleValidation;
import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class AddVehicleService {

    private final DataSource dataSource;

    public AddVehicleService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ResponseEntity<String> execute(Vehicle vehicle) {
        // Validate  vehicle
        VehicleValidation.execute(vehicle);

        String sql = "INSERT INTO vehicle " +
                "(make, model, year, license_plate, price_per_day, availability, description, image_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicle.getMake());
            stmt.setString(2, vehicle.getModel());
            stmt.setString(3, vehicle.getYear());
            stmt.setString(4, vehicle.getLicensePlate());
            stmt.setDouble(5, vehicle.getPricePerDay());
            stmt.setBoolean(6, vehicle.isAvailability());
            stmt.setString(7, vehicle.getDescription());
            stmt.setString(8, vehicle.getImageUrl());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting vehicle: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Vehicle creation failed");
        }

        return ResponseEntity.ok("Vehicle added successfully");
    }
}
