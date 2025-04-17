package com.example.CarRentalService_DbFinalProject.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Coupon {

    @Id
    @Column(name = "coupon_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int couponId;

    @Column(name = "coupon_code", nullable = false)
    private String couponCode;

    @Column(name = "discount_percentage", nullable = false)
    private double discountPercentage;

    public Coupon(double discountPercentage, String couponCode) {
        this.discountPercentage = discountPercentage;
        this.couponCode = couponCode;
    }

    public Coupon() {

    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
