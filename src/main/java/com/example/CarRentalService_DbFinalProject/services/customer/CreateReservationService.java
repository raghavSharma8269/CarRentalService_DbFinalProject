package com.example.CarRentalService_DbFinalProject.services.customer;

import com.example.CarRentalService_DbFinalProject.errorHandling.validations.ReservationValidation;
import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.model.repositories.UserRepository;
import com.example.CarRentalService_DbFinalProject.model.repositories.VehicleRepository;
import com.example.CarRentalService_DbFinalProject.security.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

@Service
public class CreateReservationService {

    private final DataSource dataSource;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final AuthUtil authUtil;

    public CreateReservationService(DataSource dataSource,
                                    UserRepository userRepository,
                                    VehicleRepository vehicleRepository,
                                    AuthUtil authUtil) {
        this.dataSource = dataSource;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.authUtil = authUtil;
    }

    public ResponseEntity<String> execute(Reservation reservation) {
        // validate reservation
        ReservationValidation.validate(reservation);

        // find the user
        String username = authUtil.getLoggedInUsername();
        Users user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // find the vehicle
        Vehicle vehicle = vehicleRepository.findById(reservation.getVehicleId().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        // sql query to insert reservation
        String insertSql = """
            INSERT INTO reservation 
              (user_id, vehicle_id, start, end, total_price) 
            VALUES (?, ?, ?, ?, ?)""";

        // sql query to update vehicle availability to false in vehicle table
        String updateSql = """
            UPDATE vehicle
               SET availability = FALSE
             WHERE vehicle_id = ?""";

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                 PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

                // insert reservation
                insertStmt.setInt(1, user.getUserId());
                insertStmt.setInt(2, vehicle.getVehicleId());
                insertStmt.setTimestamp(3, Timestamp.valueOf(reservation.getStart()));
                insertStmt.setTimestamp(4, Timestamp.valueOf(reservation.getEnd()));
                insertStmt.setDouble(5, reservation.getTotalPrice());
                insertStmt.executeUpdate();

                // mark vehicle unavailable
                updateStmt.setInt(1, vehicle.getVehicleId());
                int updated = updateStmt.executeUpdate();
                if (updated == 0) {
                    return ResponseEntity.status(500)
                            .body("Failed to update vehicle availability");
                }
            }
            return ResponseEntity.ok("Reservation created and vehicle marked unavailable");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Failed to create reservation: " + e.getMessage());
        }
    }
}
