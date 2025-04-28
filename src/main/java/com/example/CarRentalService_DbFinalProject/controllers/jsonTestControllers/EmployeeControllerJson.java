package com.example.CarRentalService_DbFinalProject.controllers.jsonTestControllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Coupon;
import com.example.CarRentalService_DbFinalProject.model.entities.Maintenance;
import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.services.employee.coupon.*;
import com.example.CarRentalService_DbFinalProject.services.employee.maintenance.*;
import com.example.CarRentalService_DbFinalProject.services.employee.reservation.GetAllReservationsService;
import com.example.CarRentalService_DbFinalProject.services.employee.reservation.GetReservationViaIdService;
import com.example.CarRentalService_DbFinalProject.services.employee.vehicle.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/json/employee")
public class EmployeeControllerJson {

    private final AddVehicleService addVehicleService;
    private final DeleteVehicleService deleteVehicleService;
    private final UpdateVehicleService updateVehicleService;
    private final GetAllVehicles getAllVehicles;
    private final GetAllReservationsService getAllReservationsService;
    private final CreateCouponService createCouponService;
    private final DeleteCouponService deleteCouponService;
    private final UpdateCouponService updateCouponService;
    private final GetCouponsService getCouponsService;
    private final CreateMaintenanceService createMaintenanceService;
    private final UpdateMaintenanceService updateMaintenanceService;
    private final GetAllMaintenanceService getAllMaintenanceService;
    private final GetMaintenanceViaIdService getMaintenanceViaIdService;
    private final GetCouponViaIdService getCouponViaIdService;
    private final GetReservationViaIdService getReservationViaIdService;
    private final GetVehicleViaIdService getVehicleViaIdService;
    private final DeleteMaintenanceService deleteMaintenanceService;

    public EmployeeControllerJson(
            AddVehicleService addVehicleService,
            DeleteVehicleService deleteVehicleService,
            UpdateVehicleService updateVehicleService,
            GetAllVehicles getAllVehicles,
            GetAllReservationsService getAllReservationsService,
            CreateCouponService createCouponService,
            DeleteCouponService deleteCouponService,
            UpdateCouponService updateCouponService,
            GetCouponsService getCouponsService,
            CreateMaintenanceService createMaintenanceService,
            UpdateMaintenanceService updateMaintenanceService,
            GetAllMaintenanceService getAllMaintenanceService,
            GetMaintenanceViaIdService getMaintenanceViaIdService,
            GetCouponViaIdService getCouponViaIdService,
            GetReservationViaIdService getReservationViaIdService,
            GetVehicleViaIdService getVehicleViaIdService,
            DeleteMaintenanceService deleteMaintenanceService
    ) {
        this.addVehicleService = addVehicleService;
        this.deleteVehicleService = deleteVehicleService;
        this.updateVehicleService = updateVehicleService;
        this.getAllVehicles = getAllVehicles;
        this.getAllReservationsService = getAllReservationsService;
        this.createCouponService = createCouponService;
        this.deleteCouponService = deleteCouponService;
        this.updateCouponService = updateCouponService;
        this.getCouponsService = getCouponsService;
        this.createMaintenanceService = createMaintenanceService;
        this.updateMaintenanceService = updateMaintenanceService;
        this.getAllMaintenanceService = getAllMaintenanceService;
        this.getMaintenanceViaIdService = getMaintenanceViaIdService;
        this.getCouponViaIdService = getCouponViaIdService;
        this.getReservationViaIdService = getReservationViaIdService;
        this.getVehicleViaIdService = getVehicleViaIdService;
        this.deleteMaintenanceService = deleteMaintenanceService;
    }

    /**
        VEHICLE CRUD OPERATIONS
     */

    @PostMapping("/vehicles")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> addVehicle (@RequestBody Vehicle vehicle) {
        return addVehicleService.execute(vehicle);
    }

    @DeleteMapping("/vehicles/{vehicleId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteVehicle(@PathVariable int vehicleId) {
        return deleteVehicleService.execute(vehicleId);
    }

//    @PutMapping("/vehicles/{vehicleId}")
//    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
//    public ResponseEntity<String> updateVehicle(@PathVariable int vehicleId, @RequestBody Vehicle updatedVehicle) {
//        return updateVehicleService.execute(vehicleId, updatedVehicle);
//    }

    @GetMapping("/vehicles")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<List<Vehicle>> getAllVehicles(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return getAllVehicles.execute(keyword, minPrice, maxPrice);
    }

    @GetMapping("/vehicles/{vehicleId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<Vehicle> getVehicleViaId(@PathVariable int vehicleId) {
        return getVehicleViaIdService.execute(vehicleId);
    }

    /**
     RESERVATION CRUD OPERATIONS
     */

    @GetMapping("/reservations")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<List<Reservation>> getAllReservations(
            @RequestParam(required = false) String keyword
    ) {
        return getAllReservationsService.execute(keyword);
    }

    @GetMapping("/reservations/{reservationId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<Reservation> getReservationViaId(
            @PathVariable int reservationId
    ) {
        return getReservationViaIdService.execute(reservationId);
    }

    /**
     COUPON CRUD OPERATIONS
     */

    @PostMapping("/coupons")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> createCoupon(@RequestBody Coupon coupon) {
        return createCouponService.execute(coupon);
    }

//    @PutMapping("/coupons/{couponId}")
//    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
//    public ResponseEntity<String> editCoupon (@RequestBody Coupon coupon, @PathVariable int couponId) {
//        return updateCouponService.execute(coupon, couponId);
//    }

    @DeleteMapping("/coupons/{couponId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteCoupon(@PathVariable int couponId) {
        return deleteCouponService.execute(couponId);
    }

    @GetMapping("/coupons")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<List<Coupon>> getAllCoupons(@RequestParam(required = false) String couponCode) {
        return getCouponsService.execute(couponCode);
    }

    @GetMapping("/coupons/{couponId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<Coupon> getCouponViaId (@PathVariable int couponId) {
        return getCouponViaIdService.execute(couponId);
    }

    /**
     MAINTENANCE CRUD OPERATIONS
     */

    @PostMapping("/maintenance")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> addMaintenance (@RequestBody Maintenance maintenance) {
        return createMaintenanceService.execute(maintenance);
    }

    @PutMapping("/maintenance/{maintenanceId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> updateMaintenance (
            @PathVariable int maintenanceId,
            @RequestBody Maintenance updatedMaintenance) {
        return updateMaintenanceService.execute(updatedMaintenance, maintenanceId);
    }

    @DeleteMapping("/maintenance/{maintenanceId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteMaintenance(@PathVariable int maintenanceId) {
        return deleteMaintenanceService.execute(maintenanceId);
    }


    @GetMapping("/maintenance")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<List<Maintenance>> getAllMaintenances(@RequestParam(required = false) String keyword) {
        return getAllMaintenanceService.execute(keyword);
    }

    @GetMapping("/maintenance/{maintenanceId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<Maintenance> getMaintenanceViaID (@PathVariable int maintenanceId) {
        return getMaintenanceViaIdService.execute(maintenanceId);
    }


}
