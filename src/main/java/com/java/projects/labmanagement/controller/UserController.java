package com.java.projects.labmanagement.controller;

import com.java.projects.labmanagement.dto.UserRequest;
import com.java.projects.labmanagement.dto.UserResponse;
import com.java.projects.labmanagement.entity.Role;
import com.java.projects.labmanagement.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }

    // Get All Users using Pageable
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getUsers(@RequestParam(defaultValue = "0")
                                                           @Min(value=0, message = "Page number cannot be negative")
                                                           int page,

                                                       @RequestParam(defaultValue = "5")
                                                           @Min(value = 1, message = "Size must be at least {min}")
                                                           @Max(value = 50, message = "Size cannot exceed {max}")
                                                           int size){

        return ResponseEntity.ok(userService.getUsers(page, size));
    }
    // Get All Users
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId){

        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponse>> getUsersByRole(@PathVariable Role role){

        List<UserResponse> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @Valid @RequestBody UserRequest request){

        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId){

        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
