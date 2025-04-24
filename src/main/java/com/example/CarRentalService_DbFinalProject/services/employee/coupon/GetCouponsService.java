package com.example.CarRentalService_DbFinalProject.services.employee.coupon;

import com.example.CarRentalService_DbFinalProject.model.entities.Coupon;
import com.example.CarRentalService_DbFinalProject.model.repositories.CouponRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCouponsService {

    private final CouponRepository couponRepository;

    public GetCouponsService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public ResponseEntity<List<Coupon>> execute (String couponCode) {

        List<Coupon> coupons = couponRepository.findAllByCouponCode(couponCode);

        return ResponseEntity.ok(coupons);

    }

}
