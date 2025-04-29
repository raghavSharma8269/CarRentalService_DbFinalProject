package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.model.repositories.VehicleRepository;
import com.example.CarRentalService_DbFinalProject.services.customer.CreateReservationService;
import com.example.CarRentalService_DbFinalProject.services.customer.GetAllAvailableVehiclesService;
import com.example.CarRentalService_DbFinalProject.services.customer.GetReservationsService;
import com.example.CarRentalService_DbFinalProject.services.profile.GetProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/customer")
public class CustomerController {

    private final VehicleRepository vehicleRepository;
    private final GetAllAvailableVehiclesService getAllAvailableVehiclesService;
    private final GetProfileService getProfileService;
    private final CreateReservationService createReservationService;
    private final GetReservationsService getReservationsService;

    public CustomerController(VehicleRepository vehicleRepository, GetAllAvailableVehiclesService getAllAvailableVehiclesService, GetProfileService getProfileService, CreateReservationService createReservationService, GetReservationsService getReservationsService) {
        this.vehicleRepository = vehicleRepository;
        this.getAllAvailableVehiclesService = getAllAvailableVehiclesService;
        this.getProfileService = getProfileService;
        this.createReservationService = createReservationService;
        this.getReservationsService = getReservationsService;
    }

    // Reservation Dashboard Page
    @GetMapping("/reservations")
    public String reservations(Model model) {
        // Fetch all reservations
        List<Reservation> reservations = getReservationsService
                .execute(null) // null keyword to fetch all reservations
                .getBody();

        // Add the reservations to the model
        model.addAttribute("reservations", reservations);

        // Set the page attribute to reservations for rendering
        model.addAttribute("page", "reservations");
        return "/pages/user-dash";
    }

    // Vehicles Dashboard Page (Vehicle.HTML page will be embedded w/Manage button)
    @GetMapping("/vehicles")
    public String listVehicles(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model
    ) {
        // fetch all vehicles (regardless of availability)
        List<Vehicle> vehicles = getAllAvailableVehiclesService
                .execute(keyword, minPrice, maxPrice)
                .getBody();

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("page", "vehicles");
        return "pages/user-dash";
    }


    // Load details page when you click into a car
    @GetMapping("/vehicles/{id}")
    public String showVehicleDetails(@PathVariable int id, Model model) {
        // Check for the vehicle in the database
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);

        model.addAttribute("page", "vehicleDetails");

        // If the vehicle is found, add it to the model otherwise add an error message
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
        } else {
            model.addAttribute("message", "Vehicle not found.");
        }

        return "/pages/user-dash";
    }

    // Load the checkout page when you click on a 'Rent Now' button
    @GetMapping("/checkout/{id}")
    public String showCheckout(@PathVariable int id, Model model, Principal principal) {
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(id);

        // Check for the vehicle in the database
        if (vehicleOpt.isPresent()) {
            Vehicle vehicle = vehicleOpt.get();

            // Retrieve the current user from getProfileService, passing thought the user's name from principal
            Users user = getProfileService.findByUsername(principal.getName());

            // Create a new Reservation and preset the user and vehicle objects
            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setVehicleId(vehicle);

            // Debugging System.out.println("Current Reservation: " + reservation);

            // Add reservation, vehicle, and rendering page to the model
            model.addAttribute("reservationCheckoutForm", reservation);
            model.addAttribute("vehicle", vehicle);
            model.addAttribute("page", "vehicleCheckout");

            return "/pages/user-dash";
        } else {
            model.addAttribute("message", "Vehicle not found.");
            return "redirect:/api/dashboard/vehicles";
        }
    }

    // Call CreateReservationService to create a reservation at the checkout page
    // IMPORTANT: THIS ENDPOINT IS SHARED FOR ALL USERS
    @PostMapping("/checkout")
    public String createReservation(@ModelAttribute("reservationCheckoutForm") Reservation reservation, RedirectAttributes redirectAttrs) {
        try {
            // Debugging System.out.println("Creating reservation: " + reservation);

            // Call the service to create the reservation
            createReservationService.execute(reservation);
            redirectAttrs.addFlashAttribute("success", "Reservation Created Successfully!");
        } catch (Exception ex) {
            redirectAttrs.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/dashboard"; // Redirect to the reservation page after creating the reservation
    }

}
