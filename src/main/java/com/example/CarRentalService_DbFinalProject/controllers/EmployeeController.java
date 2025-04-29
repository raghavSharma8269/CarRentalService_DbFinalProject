package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Reservation;
import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.model.entities.Vehicle;
import com.example.CarRentalService_DbFinalProject.model.repositories.VehicleRepository;
import com.example.CarRentalService_DbFinalProject.services.employee.reservation.GetAllReservationsService;
import com.example.CarRentalService_DbFinalProject.services.employee.vehicle.*;
import com.example.CarRentalService_DbFinalProject.services.profile.GetProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/employee")
public class EmployeeController {

    private final VehicleRepository vehicleRepository;
    private final GetAllVehicles getAllVehicles;
    private final GetVehicleViaIdService getVehicleViaIdService;
    private final UpdateVehicleService updateVehicleService;
    private final AddVehicleService addVehicleService;
    private final DeleteVehicleService deleteVehicleService;
    private final GetProfileService getProfileService;
    private final GetAllReservationsService getAllReservationsService;

    public EmployeeController(
            VehicleRepository vehicleRepository,
            GetAllVehicles getAllVehicles,
            GetVehicleViaIdService getVehicleViaIdService,
            UpdateVehicleService updateVehicleService, AddVehicleService addVehicleService, DeleteVehicleService deleteVehicleService, GetProfileService getProfileService, GetAllReservationsService getAllReservationsService
    ) {
        this.vehicleRepository = vehicleRepository;
        this.getAllVehicles = getAllVehicles;
        this.getVehicleViaIdService = getVehicleViaIdService;
        this.updateVehicleService = updateVehicleService;
        this.addVehicleService = addVehicleService;
        this.deleteVehicleService = deleteVehicleService;
        this.getProfileService = getProfileService;
        this.getAllReservationsService = getAllReservationsService;
    }


    // Reservation Dashboard Page
    @GetMapping("/reservations")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public String reservations(Model model) {
        // fetch all reservations
        List<Reservation> reservations = getAllReservationsService
                .execute(null) // null keyword to fetch all reservations
                .getBody();

        // Add the reservations to the model
        model.addAttribute("reservations", reservations);

        System.out.println("reservations: " + reservations);

        // Set the page attribute to reservations for rendering
        model.addAttribute("page", "reservations");

        return "/pages/user-dash";
    }

    @GetMapping("/vehicles")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public String listVehicles(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model
    ) {
        // fetch all vehicles
        List<Vehicle> vehicles = getAllVehicles
                .execute(keyword, minPrice, maxPrice)
                .getBody();

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("page", "vehicles");
        // renders src/main/resources/templates/pages/user-dash.html
        return "pages/user-dash";
    }

    // Load details page when you click into a car
    @GetMapping("/vehicles/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public String showVehicleDetails(@PathVariable int id, Model model) {
        // Check for the vehicle in the database
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);

        // Set the page attribute to vehicleDetails for rendering
        model.addAttribute("page", "vehicleDetails");

        // If the vehicle is found, add it to the model otherwise add an error message
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
        } else {
            model.addAttribute("message", "Vehicle not found.");
        }

        // Return the vehicle-card.html fragment to be rendered
        return "/pages/user-dash";
    }

    // Load the checkout page when you click on a 'Rent Now' button
    @GetMapping("/checkout/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
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

            // Debugging
            System.out.println("Current Reservation: " + reservation);

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

    // Maintenance Dashboard Page
    @GetMapping("/maintenance")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public String maintenance(Model model) {
        model.addAttribute("page", "maintenance");
        return "/pages/user-dash";
    }

    // Coupons Dashboard Page
    @GetMapping("/coupons")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public String coupons(Model model) {
        model.addAttribute("page", "coupons");
        return "/pages/user-dash";
    }

    // Show the “Edit Vehicle” form
    @GetMapping("/vehicles/manage/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public String showEditPage(@PathVariable int id, Model model) {
        Vehicle vehicle = getVehicleViaIdService.execute(id).getBody();
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("page", "vehicleEdit");
        return "pages/user-dash";
    }

    // Process the submitted manage vehicle form
    //Edits vehicle details
    @PostMapping("/vehicles/manage/{id}")
    public String updateVehicle(
            @PathVariable int id,
            @ModelAttribute("vehicle") Vehicle vehicleFormData,
            RedirectAttributes redirectAttrs
    ) {
        vehicleFormData.setVehicleId(id);
        try {
            updateVehicleService.execute(vehicleFormData);
            redirectAttrs.addFlashAttribute("success", "Vehicle updated successfully!");
        } catch (Exception ex) {
            redirectAttrs.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/dashboard/employee/vehicles/manage/"+id;
    }

    // Process the delete vehicle form
    @PostMapping("/vehicles/manage/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public String deleteVehicle(@PathVariable int id, RedirectAttributes redirectAttrs) {
        try {
            deleteVehicleService.execute(id);
            redirectAttrs.addFlashAttribute("success", "Vehicle deleted successfully!");
        } catch (Exception ex) {
            redirectAttrs.addFlashAttribute("error", "Failed to delete vehicle: " + ex.getMessage());
        }
        return "redirect:/dashboard/employee/vehicles";
    }


    // Show the “Add Vehicle” form
    @GetMapping("/vehicles/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public String showAddVehiclePage (Model model) {
        // Create a new Vehicle object to bind to the form
        Vehicle vehicle = new Vehicle();

        // Add the vehicle object to the model
        model.addAttribute("vehicle", vehicle);

        // Set the page attribute to addVehicle for rendering
        model.addAttribute("page", "addVehicle");
        return "pages/user-dash";
    }

    // Call AddVehicleService to add a vehicle into the database
    @PostMapping("/vehicles/add")
    public String addVehicle(
            // RedirectAttribute is used to pass flash attributes (temporary data) to the redirected page (Such as success/error messages)
            @ModelAttribute("vehicle") Vehicle vehicleFormData,
            RedirectAttributes redirectAttrs
    ) {
        try {
            addVehicleService.execute(vehicleFormData);
            redirectAttrs.addFlashAttribute("success", "Vehicle added successfully!");
        } catch (Exception ex) {
            redirectAttrs.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/dashboard/employee/vehicles/add";
    }

}