package com.example.CarRentalService_DbFinalProject.controllers.jsonTestControllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.services.admin.AddEmployeeService;
import com.example.CarRentalService_DbFinalProject.services.admin.GetAllUsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/json/admin")
public class AdminControllerJson {

    private final AddEmployeeService addEmployeeService;
    private final GetAllUsersService getAllUsersService;

    public AdminControllerJson(AddEmployeeService addEmployeeService, GetAllUsersService getAllUsersService) {
        this.addEmployeeService = addEmployeeService;
        this.getAllUsersService = getAllUsersService;
    }

    @PostMapping("/add-employee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addEmployee(@RequestBody Users user) {
        addEmployeeService.execute(user);
        return ResponseEntity.ok("Employee added successfully");
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Users>> getAllUsers(@RequestParam (required = false) String role) {
        List<Users> users = getAllUsersService.execute(role);
        return ResponseEntity.ok(users);
    }


}
