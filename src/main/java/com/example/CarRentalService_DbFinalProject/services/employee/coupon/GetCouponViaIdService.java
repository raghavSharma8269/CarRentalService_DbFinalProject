package com.example.CarRentalService_DbFinalProject.services.employee.coupon;

import com.example.CarRentalService_DbFinalProject.model.entities.Coupon;
import com.example.CarRentalService_DbFinalProject.model.repositories.CouponRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetCouponViaIdService {

    private final CouponRepository couponRepository;


    public GetCouponViaIdService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public ResponseEntity<Coupon> execute (int couponId) {

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        return ResponseEntity.ok(coupon);

    }
}
