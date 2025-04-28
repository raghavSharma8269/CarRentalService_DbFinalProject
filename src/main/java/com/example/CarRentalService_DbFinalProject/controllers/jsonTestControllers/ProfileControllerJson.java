package com.example.CarRentalService_DbFinalProject.controllers.jsonTestControllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.services.profile.ChangePasswordRequest;
import com.example.CarRentalService_DbFinalProject.services.profile.ChangePasswordService;
import com.example.CarRentalService_DbFinalProject.services.profile.GetProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/json/profile")
public class ProfileControllerJson {

    private final GetProfileService getProfileService;
    private final ChangePasswordService changePasswordService;

    public ProfileControllerJson(GetProfileService getProfileService, ChangePasswordService changePasswordService) {
        this.getProfileService = getProfileService;
        this.changePasswordService = changePasswordService;
    }
//
//    @GetMapping
//    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
//    public ResponseEntity<Users> getProfile() {
//        return getProfileService.findByUsername();
//    }

    @PutMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        return changePasswordService.execute(
                request.getCurrentPassword(),
                request.getNewPassword(),
                request.getConfirmNewPassword()
        );
    }


}
