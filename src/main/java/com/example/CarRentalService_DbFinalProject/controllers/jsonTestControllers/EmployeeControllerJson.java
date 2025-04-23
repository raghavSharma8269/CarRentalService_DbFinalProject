package com.example.CarRentalService_DbFinalProject.controllers.jsonTestControllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Coupon;
import com.example.CarRentalService_DbFinalProject.model.entities.Maintenance;
import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.services.employee.coupon.CreateCouponService;
import com.example.CarRentalService_DbFinalProject.services.employee.coupon.DeleteCouponService;
import com.example.CarRentalService_DbFinalProject.services.employee.coupon.EditCouponService;
import com.example.CarRentalService_DbFinalProject.services.employee.coupon.GetCouponsService;
import com.example.CarRentalService_DbFinalProject.services.employee.maintenance.CreateMaintenanceService;
import com.example.CarRentalService_DbFinalProject.services.employee.maintenance.GetAllMaintenanceService;
import com.example.CarRentalService_DbFinalProject.services.employee.maintenance.GetMaintenanceViaIdService;
import com.example.CarRentalService_DbFinalProject.services.employee.maintenance.UpdateMaintenanceService;
import com.example.CarRentalService_DbFinalProject.services.employee.reservation.GetAllReservationsService;
import com.example.CarRentalService_DbFinalProject.services.employee.vehicle.AddVehicleService;
import com.example.CarRentalService_DbFinalProject.services.employee.vehicle.DeleteVehicleService;
import com.example.CarRentalService_DbFinalProject.services.employee.vehicle.GetAllVehicles;
import com.example.CarRentalService_DbFinalProject.services.employee.vehicle.UpdateVehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
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
    private final EditCouponService editCouponService;
    private final GetCouponsService getCouponsService;
    private final CreateMaintenanceService createMaintenanceService;
    private final UpdateMaintenanceService updateMaintenanceService;
    private final GetAllMaintenanceService getAllMaintenanceService;
    private final GetMaintenanceViaIdService getMaintenanceViaIdService;

    public EmployeeControllerJson(
            AddVehicleService addVehicleService,
            DeleteVehicleService deleteVehicleService,
            UpdateVehicleService updateVehicleService,
            GetAllVehicles getAllVehicles,
            GetAllReservationsService getAllReservationsService,
            CreateCouponService createCouponService,
            DeleteCouponService deleteCouponService,
            EditCouponService editCouponService,
            GetCouponsService getCouponsService,
            CreateMaintenanceService createMaintenanceService,
            UpdateMaintenanceService updateMaintenanceService,
            GetAllMaintenanceService getAllMaintenanceService,
            GetMaintenanceViaIdService getMaintenanceViaIdService
    ) {
        this.addVehicleService = addVehicleService;
        this.deleteVehicleService = deleteVehicleService;
        this.updateVehicleService = updateVehicleService;
        this.getAllVehicles = getAllVehicles;
        this.getAllReservationsService = getAllReservationsService;
        this.createCouponService = createCouponService;
        this.deleteCouponService = deleteCouponService;
        this.editCouponService = editCouponService;
        this.getCouponsService = getCouponsService;
        this.createMaintenanceService = createMaintenanceService;
        this.updateMaintenanceService = updateMaintenanceService;
        this.getAllMaintenanceService = getAllMaintenanceService;
        this.getMaintenanceViaIdService = getMaintenanceViaIdService;
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

    @PutMapping("/vehicles/{vehicleId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> updateVehicle(@PathVariable int vehicleId, @RequestBody Vehicle updatedVehicle) {
        return updateVehicleService.execute(vehicleId, updatedVehicle);
    }

    @GetMapping("/vehicles")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<List<Vehicle>> getAllVehicles(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return getAllVehicles.execute(keyword, minPrice, maxPrice);
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

    /**
     COUPON CRUD OPERATIONS
     */

    @PostMapping("/coupons")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> createCoupon(@RequestBody Coupon coupon) {
        return createCouponService.execute(coupon);
    }

    @PutMapping("/coupons/{couponId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> editCoupon (@RequestBody Coupon coupon, @PathVariable int couponId) {
        return editCouponService.execute(coupon, couponId);
    }

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
