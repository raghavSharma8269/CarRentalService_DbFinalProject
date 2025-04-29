package com.example.CarRentalService_DbFinalProject.controllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.services.admin.*;
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
    private final DeleteUserService deleteUserService;
    private final UpdateUserService updateUserService;
    private final GetUserViaIdService getUserViaIdService;

    public AdminController(
            GetAllUsersService getAllUsersService,
            AddEmployeeService addEmployeeService,
            DeleteUserService deleteUserService,
            UpdateUserService updateUserService,
            GetUserViaIdService getUserViaIdService
    ) {
        this.getAllUsersService = getAllUsersService;
        this.addEmployeeService = addEmployeeService;
        this.deleteUserService = deleteUserService;
        this.updateUserService = updateUserService;
        this.getUserViaIdService = getUserViaIdService;
    }


    // show all accounts page
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

        model.addAttribute("role", role);
        model.addAttribute("keyword", keyword);

        return "/pages/user-dash";
    }

    // show add employee page
    @GetMapping("/accounts/add-employee")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddEmployee(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("page", "addEmployee");
        return "/pages/user-dash";
    }

    //Add user (not just employee)
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

    @GetMapping("/accounts/manage/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showManageAccounts(@PathVariable int id, Model model) {

        Users user = getUserViaIdService.execute(id).getBody();
        model.addAttribute("user", user);
        model.addAttribute("page", "accountEdit");
        return "/pages/user-dash";
    }

    @PostMapping("/accounts/manage/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String manageAccounts(
            @PathVariable int id,
            @ModelAttribute("user") Users user,
            RedirectAttributes redirectAttributes
    )
    {
        user.setUserId(id);

        try {
            updateUserService.execute(user, id);
            redirectAttributes.addFlashAttribute("success", "User updated successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/dashboard/admin/accounts/manage/" + id;
    }

    @PostMapping("/accounts/manage/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(
            @PathVariable int id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            deleteUserService.execute(id);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/dashboard/admin/accounts";
    }

}
