package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.services.admin.AddEmployeeService;
import com.example.CarRentalService_DbFinalProject.services.admin.GetAllUsersService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dashboard/admin")
public class AdminController {

    private final GetAllUsersService getAllUsersService;
    private final AddEmployeeService addEmployeeService;

    public AdminController(
            GetAllUsersService getAllUsersService,
                           AddEmployeeService addEmployeeService
    ) {
        this.getAllUsersService = getAllUsersService;
        this.addEmployeeService = addEmployeeService;
    }


    // Account Management Dashboard Page
    @GetMapping("/accounts")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAccounts(
            Model model,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String keyword
    ) {
        List<Users> users = getAllUsersService.execute(role, keyword);
        model.addAttribute("users", users);
        model.addAttribute("page", "accounts");
        return "/pages/user-dash";
    }

    @GetMapping("/accounts/add-employee")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddEmployee(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("page", "addEmployee");
        return "/pages/user-dash";
    }


    @PostMapping("/accounts/add-employee")
    @PreAuthorize("hasRole('ADMIN')")
    public String addEmployee(
            @ModelAttribute("user") Users employeeForm,
            RedirectAttributes redirectAttributes
    ){
        try {
            addEmployeeService.execute(employeeForm);
            redirectAttributes.addFlashAttribute("success", "User created successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/dashboard/admin/accounts/add-employee";
    }


}
