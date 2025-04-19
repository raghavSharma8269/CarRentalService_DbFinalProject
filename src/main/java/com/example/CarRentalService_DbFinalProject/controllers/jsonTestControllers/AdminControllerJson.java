package com.example.CarRentalService_DbFinalProject.controllers.jsonTestControllers;

import com.example.CarRentalService_DbFinalProject.model.entities.Users;
import com.example.CarRentalService_DbFinalProject.services.admin.AddEmployeeService;
import com.example.CarRentalService_DbFinalProject.services.admin.DeleteUserService;
import com.example.CarRentalService_DbFinalProject.services.admin.GetAllUsersService;
import com.example.CarRentalService_DbFinalProject.services.admin.UpdateUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/json/admin")
public class AdminControllerJson {

    private final AddEmployeeService addEmployeeService;
    private final GetAllUsersService getAllUsersService;
    private final DeleteUserService deleteUserService;
    private final UpdateUserService updateUserService;

    public AdminControllerJson(AddEmployeeService addEmployeeService, GetAllUsersService getAllUsersService, DeleteUserService deleteUserService, UpdateUserService updateUserService) {
        this.addEmployeeService = addEmployeeService;
        this.getAllUsersService = getAllUsersService;
        this.deleteUserService = deleteUserService;
        this.updateUserService = updateUserService;
    }

    @PostMapping("/add-employee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addEmployee(@RequestBody Users user) {
        addEmployeeService.execute(user);
        return ResponseEntity.ok("Employee added successfully");
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Users>> getAllUsers(
            @RequestParam (required = false) String role,
            @RequestParam (required = false) String keyword
    ) {
        List<Users> users = getAllUsersService.execute(role, keyword);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        return deleteUserService.execute(userId);
    }

    @PutMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUser(@RequestBody Users updatedUser, @PathVariable int userId) {
        return updateUserService.execute(updatedUser, userId);
    }


}
