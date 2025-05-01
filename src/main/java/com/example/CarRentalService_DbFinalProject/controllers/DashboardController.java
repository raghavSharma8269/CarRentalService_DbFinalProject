package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.services.employee.reservation.ModifyReservationsService;
import com.example.CarRentalService_DbFinalProject.services.profile.GetProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final GetProfileService getProfileService;
    private final ModifyReservationsService modifyReservationsService;

    public DashboardController(GetProfileService getProfileService, ModifyReservationsService modifyReservationsService) {
        this.getProfileService = getProfileService;
        this.modifyReservationsService = modifyReservationsService;
    }

    @GetMapping
    public String dashboard(Principal principal, Model model) {
        // Loads the currently logged-in user
        Users user = getProfileService.findByUsername(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("page", "dashboard");
        return "pages/user-dash";
    }

    @PostMapping("/reservation/modify/{id}")
    public String updateReservation(
            @PathVariable int id,
            @RequestParam("start") LocalDateTime start,
            @RequestParam("end") LocalDateTime end,
            @RequestParam("totalPriceHidden") double totalPrice,
            Model model,
            Principal principal) {

        try {
            // Perform the update using the service
            modifyReservationsService.updateReservation(id, start, end, totalPrice);
        } catch (Exception e) {
            // Handle any errors during the update
            model.addAttribute("error", "Failed to update reservation: " + e.getMessage());
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/reservation/cancel/{reservationId}")
    public String cancelReservation(
            @PathVariable int reservationId,
            RedirectAttributes redirectAttributes) {

        try {
            // Call the service to cancel/delete the reservation
            modifyReservationsService.cancelReservation(reservationId);
            redirectAttributes.addFlashAttribute("success", "Reservation successfully canceled.");
        } catch (Exception e) {
            // Handle any errors during cancellation
            redirectAttributes.addFlashAttribute("error", "Failed to cancel reservation: " + e.getMessage());
        }

        return "redirect:/dashboard";
    }
}
