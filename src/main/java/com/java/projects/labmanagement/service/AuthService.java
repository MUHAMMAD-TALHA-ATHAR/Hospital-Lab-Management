package com.java.projects.labmanagement.service;
import com.java.projects.labmanagement.dto.auth.*;
import com.java.projects.labmanagement.entity.User;
import com.java.projects.labmanagement.exception.BadRequestException;
import com.java.projects.labmanagement.exception.UnauthorizedException;
import com.java.projects.labmanagement.mapper.UserMapper;
import com.java.projects.labmanagement.repository.UserRepository;
import com.java.projects.labmanagement.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, UserMapper userMapper, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // register user
    @Transactional
    public AuthResponse register(RegisterRequest request){

        if (userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("User with email " + request.getEmail() + " already exists");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        return userMapper.toAuthResponse(savedUser, token);
    }

    // login user
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        String token = jwtService.generateToken(user);

        return userMapper.toAuthResponse(user, token);
    }
}