package com.example.CarRentalService_DbFinalProject.model.repositories;

import com.example.CarRentalService_DbFinalProject.model.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    @Query("SELECT c FROM Coupon c WHERE :couponCode " +
            "IS NULL OR :couponCode = '' " +
            "OR LOWER(c.couponCode) LIKE LOWER(CONCAT('%', :couponCode, '%'))")
    List<Coupon> findAllByCouponCode(String couponCode);
}
