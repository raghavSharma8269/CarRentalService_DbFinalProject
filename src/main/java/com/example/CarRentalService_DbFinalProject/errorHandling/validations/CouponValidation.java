package com.example.CarRentalService_DbFinalProject.errorHandling.validations;

import com.example.CarRentalService_DbFinalProject.model.entities.Coupon;

public class CouponValidation {

    public static void validate (Coupon coupon) {

        if (coupon.getCouponCode() == null || coupon.getCouponCode().isEmpty()) {
            throw new IllegalArgumentException("Coupon code cannot be null or empty");
        }

        if (coupon.getDiscountPercentage() <= 0 || coupon.getDiscountPercentage() > 1) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 1");
        }
    }

}
