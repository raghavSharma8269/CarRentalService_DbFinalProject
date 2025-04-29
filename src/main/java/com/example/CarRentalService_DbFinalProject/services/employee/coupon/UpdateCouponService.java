package com.example.CarRentalService_DbFinalProject.services.employee.coupon;

import com.example.CarRentalService_DbFinalProject.model.entities.Coupon;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Service
public class UpdateCouponService {

    private final DataSource dataSource;

    public UpdateCouponService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ResponseEntity<String> execute(Coupon coupon, int id) {

        // SQL query to update the coupon via coupon_id
        String sql = "UPDATE coupon SET coupon_code = ?, discount_percentage = ? WHERE coupon_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("Updating coupon with ID: " + coupon.getCouponId());

            stmt.setString(1, coupon.getCouponCode());
            stmt.setDouble(2, coupon.getDiscountPercentage());
            stmt.setInt(3, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                return ResponseEntity.badRequest().body("Coupon ID not found");
            }

            return ResponseEntity.ok("Coupon updated successfully");

        } catch (Exception e) {
            System.out.println("Error updating coupon: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Coupon update failed");
        }
    }
}
