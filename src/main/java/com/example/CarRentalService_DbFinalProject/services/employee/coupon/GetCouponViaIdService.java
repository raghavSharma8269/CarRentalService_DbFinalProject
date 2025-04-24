package com.example.CarRentalService_DbFinalProject.services.employee.coupon;

import com.example.CarRentalService_DbFinalProject.model.entities.Coupon;
import com.example.CarRentalService_DbFinalProject.model.repositories.CouponRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GetCouponViaIdService {

    private final CouponRepository couponRepository;


    public GetCouponViaIdService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public ResponseEntity<Coupon> execute (int couponId) {

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Coupon not found with id: " + couponId));


        return ResponseEntity.ok(coupon);

    }
}
