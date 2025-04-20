package com.example.CarRentalService_DbFinalProject.services.employee;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class DeleteVehicleService {

    private final DataSource dataSource;

    public DeleteVehicleService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void execute(int vehicleId) {
        String sql = "DELETE FROM vehicle WHERE vehicle_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vehicleId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deleting vehicle: " + e.getMessage());
        }
    }
}
