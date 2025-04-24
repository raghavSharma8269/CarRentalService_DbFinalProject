package com.example.CarRentalService_DbFinalProject.services.employee.vehicle;

import com.example.CarRentalService_DbFinalProject.model.repositories.MaintenanceRepository;
import com.example.CarRentalService_DbFinalProject.model.repositories.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class DeleteVehicleService {

    private final DataSource dataSource;
    private final ReservationRepository reservationRepository;
    private final MaintenanceRepository maintenanceRepository;

    public DeleteVehicleService(
            DataSource dataSource,
            ReservationRepository reservationRepository,
            MaintenanceRepository maintenanceRepository
    ) {
        this.dataSource = dataSource;
        this.reservationRepository = reservationRepository;
        this.maintenanceRepository = maintenanceRepository;
    }

    public ResponseEntity<String> execute(int vehicleId) {
        String sql = "DELETE FROM vehicle WHERE vehicle_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vehicleId);

            // Delete associated reservations and maintenance
            reservationRepository.deleteReservationByVehicleId(vehicleId);
            maintenanceRepository.deleteMaintenanceByVehicleId(vehicleId);

            int numRowsAffected = stmt.executeUpdate();
            if (numRowsAffected <= 0) {
                return ResponseEntity.badRequest().body("Vehicle ID not found");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting vehicle: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Vehicle deletion failed");
        }
        return ResponseEntity.ok("Vehicle deleted successfully");
    }
}
