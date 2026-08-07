package com.java.projects.labmanagement.controller;

import com.java.projects.labmanagement.dto.user.UserRequest;
import com.java.projects.labmanagement.dto.user.UserResponse;
import com.java.projects.labmanagement.enums.Role;
import com.java.projects.labmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
@Tag(name = "Users", description = "User management APIs")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "Get all users (paginated)", description = "Admin can view all users with pagination")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getUsers(@RequestParam(defaultValue = "0") @Min(value = 0, message = "Page number cannot be negative") int page,

                                                       @RequestParam(defaultValue = "5") @Min(value = 1, message = "Size must be at least {min}") @Max(value = 50, message = "Size cannot exceed {max}") int size) {

        return ResponseEntity.ok(userService.getUsers(page, size));
    }

    @Operation(summary = "Get all users", description = "Admin can retrieve all users without pagination")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Get user by ID", description = "Admin can fetch a user by their ID")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @Operation(summary = "Get users by role", description = "Admin can fetch users filtered by role")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponse>> getUsersByRole(@PathVariable Role role) {

        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @Operation(summary = "Update user", description = "Admin can update any user's details")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @Valid @RequestBody UserRequest request) {

        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    @Operation(summary = "Delete user", description = "Admin can delete a user by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {

        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Get current user profile", description = "Authenticated user can view their own profile")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {

        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @Operation(summary = "Update current user profile", description = "Authenticated user can update their own profile")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(@Valid @RequestBody UserRequest request) {

        return ResponseEntity.ok(userService.updateCurrentUser(request));
    }
}