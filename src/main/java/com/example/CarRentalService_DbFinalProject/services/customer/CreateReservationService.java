package com.example.CarRentalService_DbFinalProject.services.customer;

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
import java.time.LocalDateTime;

@Service
public class CreateReservationService {

    private final DataSource dataSource;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final AuthUtil authUtil;

    public CreateReservationService(DataSource dataSource, UserRepository userRepository,
                                    VehicleRepository vehicleRepository, AuthUtil authUtil) {
        this.dataSource = dataSource;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.authUtil = authUtil;
    }

    public ResponseEntity<String> execute(LocalDateTime start, LocalDateTime end, int vehicleId, double totalPrice) {
        String username = authUtil.getLoggedInUsername();

        Users user = userRepository.findByUserName(username).orElseThrow(() ->
                new RuntimeException("User not found"));

        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() ->
                new RuntimeException("Vehicle not found"));

        String sql = "INSERT INTO reservation (user_id, vehicle_id, start, end, total_price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getUserId());
            stmt.setInt(2, vehicle.getVehicleId());
            stmt.setTimestamp(3, Timestamp.valueOf(start));
            stmt.setTimestamp(4, Timestamp.valueOf(end));
            stmt.setDouble(5, totalPrice);

            stmt.executeUpdate();
            return ResponseEntity.ok("Reservation created");

        } catch (Exception e) {
            System.out.println("Error creating reservation: " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to create reservation");
        }
    }
}
