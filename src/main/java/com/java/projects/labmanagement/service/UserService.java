package com.java.projects.labmanagement.service;

import com.java.projects.labmanagement.dto.UserRequest;
import com.java.projects.labmanagement.dto.UserResponse;
import com.java.projects.labmanagement.entity.Role;
import com.java.projects.labmanagement.entity.User;
import com.java.projects.labmanagement.exception.ResourceNotFoundException;
import com.java.projects.labmanagement.mapper.UserMapper;
import com.java.projects.labmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // Create User
    @Transactional
    public UserResponse createUser(UserRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("User with email " + request.getEmail() + " already exists");
        }

        User user = userMapper.toEntity(request);

        return userMapper.toResponse(userRepository.save(user));
    }

    // Get All Users using Pageable
    @Transactional(readOnly = true)
    public Page<UserResponse> getUsers(int page, int size){

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    // Get All Users
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers(){

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    // Get User by id
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        return userMapper.toResponse(user);
    }

    // Get User by Role
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByRole(Role role){

        return userRepository.findByRole(role)
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    // Update User
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        if(!user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmailAndIdNot(request.getEmail(), id)){
            throw new IllegalArgumentException("Email already in use");
        }
        
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());

        return userMapper.toResponse(userRepository.save(user));
    }

    // Delete User
    @Transactional
    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        userRepository.delete(user);
    }

}
