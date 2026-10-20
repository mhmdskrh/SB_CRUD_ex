package com.example.employee_api.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.employee_api.dto.AuthenticationRequest;
import com.example.employee_api.dto.AuthenticationResponse;
import com.example.employee_api.dto.RegisterRequest;
import com.example.employee_api.entity.Role;
import com.example.employee_api.entity.User;
import com.example.employee_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * AuthenticationService
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {
        final User user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("u not found"));
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();

    }

}
