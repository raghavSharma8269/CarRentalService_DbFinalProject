package com.example.CarRentalService_DbFinalProject.services.employee.coupon;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Service
public class DeleteCouponService {

    private final DataSource dataSource;

    public DeleteCouponService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ResponseEntity<String> execute(int couponId) {

        // SQL query to delete the coupon via coupon_id
        String sql = "DELETE FROM coupon WHERE coupon_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, couponId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                return ResponseEntity.badRequest().body("Coupon ID not found");
            }

            return ResponseEntity.ok("Coupon deleted successfully");

        } catch (Exception e) {
            System.out.println("Error deleting coupon: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Coupon deletion failed");
        }
    }
}
