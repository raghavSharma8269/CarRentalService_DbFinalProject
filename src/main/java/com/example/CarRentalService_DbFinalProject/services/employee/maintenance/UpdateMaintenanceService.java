package com.example.CarRentalService_DbFinalProject.services.employee.maintenance;

import com.example.CarRentalService_DbFinalProject.model.entities.Maintenance;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

@Service
public class UpdateMaintenanceService {

    private final DataSource dataSource;

    public UpdateMaintenanceService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ResponseEntity<String> execute (Maintenance updatedMaintenance, int maintenanceId) {

        // SQL query to update a maintenance record via maintenance_id
        String sql = "UPDATE maintenance SET vehicle_id = ?, start = ?, end = ?, description = ? WHERE maintenance_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, updatedMaintenance.getVehicleId().getVehicleId());
            stmt.setDate(2, Date.valueOf(updatedMaintenance.getStart()));
            stmt.setDate(3, Date.valueOf(updatedMaintenance.getEnd()));
            stmt.setString(4, updatedMaintenance.getDescription());
            stmt.setInt(5, maintenanceId);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected < 0) {
                return ResponseEntity.badRequest().body("Maintenance ID not found");
            }


            return ResponseEntity.ok("Maintenance updated successfully");

        } catch (Exception e) {
            System.out.println("Error updating Maintenance: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Maintenance update failed");
        }


    }

}
