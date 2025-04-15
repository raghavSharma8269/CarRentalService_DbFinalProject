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

}
