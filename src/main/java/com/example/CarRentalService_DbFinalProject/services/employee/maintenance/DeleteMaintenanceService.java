package com.example.CarRentalService_DbFinalProject.services.employee.maintenance;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Service
public class DeleteMaintenanceService {

    private final DataSource dataSource;

    public DeleteMaintenanceService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ResponseEntity<String> execute(int maintenanceId) {
        String sql = "DELETE FROM maintenance WHERE maintenance_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maintenanceId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                return ResponseEntity.badRequest().body("Maintenance ID not found");
            }

            return ResponseEntity.ok("Maintenance deleted successfully");

        } catch (Exception e) {
            System.out.println("Error deleting Maintenance: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Maintenance deletion failed");
        }
    }
}
