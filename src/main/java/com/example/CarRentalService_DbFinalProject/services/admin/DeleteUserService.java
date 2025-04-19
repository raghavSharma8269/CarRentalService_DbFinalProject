package com.example.CarRentalService_DbFinalProject.services.admin;

import com.example.CarRentalService_DbFinalProject.model.repositories.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class DeleteUserService {

    private final ReservationRepository reservationRepository;
    private final DataSource dataSource;

    public DeleteUserService(ReservationRepository reservationRepository, DataSource dataSource) {
        this.reservationRepository = reservationRepository;
        this.dataSource = dataSource;
    }

    public ResponseEntity<String> execute(int userId) {

        reservationRepository.deleteReservationByUserId(userId);

        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }

        return ResponseEntity.ok("Deleted user ");
    }

}
