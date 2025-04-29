package com.example.CarRentalService_DbFinalProject.services.employee.coupon;

import com.example.CarRentalService_DbFinalProject.errorHandling.validations.CouponValidation;
import com.example.CarRentalService_DbFinalProject.model.entities.Coupon;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Service
public class CreateCouponService {

    private final DataSource dataSource;

    public CreateCouponService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ResponseEntity<String> execute(Coupon coupon) {

        // Validate the coupon
        CouponValidation.validate(coupon);

        // SQL query to insert the coupon
        String sql = "INSERT INTO coupon (coupon_code, discount_percentage) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, coupon.getCouponCode());
            stmt.setDouble(2, coupon.getDiscountPercentage());

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error adding coupon: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Coupon creation failed");

        }

        return ResponseEntity.ok("Successfully added coupon");
    }

}
