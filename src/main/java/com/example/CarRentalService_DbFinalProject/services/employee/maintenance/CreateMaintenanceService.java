package com.example.CarRentalService_DbFinalProject.services.employee.maintenance;

import com.example.CarRentalService_DbFinalProject.errorHandling.validations.MaintenanceValidation;
import com.example.CarRentalService_DbFinalProject.model.entities.Maintenance;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

@Service
public class CreateMaintenanceService {

    private final DataSource dataSource;

    public CreateMaintenanceService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ResponseEntity<String> execute (Maintenance maintenance) {

        MaintenanceValidation.validate(maintenance);


        // Adds maintenance to the database
        String sql = "INSERT INTO maintenance (vehicle_id, start, end, description) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maintenance.getVehicleId().getVehicleId());
            stmt.setDate(2, Date.valueOf(maintenance.getStart()));
            stmt.setDate(3, Date.valueOf(maintenance.getEnd()));
            stmt.setString(4, maintenance.getDescription());


            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error adding to maintenance: " + e.getMessage());
        }



        // Sets vehicle availability to false
        String sql2 = "UPDATE vehicle SET availability = ? WHERE vehicle_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql2)) {

            stmt.setBoolean(1, false); // availability = false
            stmt.setInt(2, maintenance.getVehicleId().getVehicleId());

            stmt.executeUpdate();
        }catch (Exception e) {
            System.out.println("Error updating vehicle to unavailable: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Failed to create maintenance");
        }


        return ResponseEntity.ok("Added Vehicle to Maintenance");

    }

}
