package com.example.bankingsystem.Service;

import com.example.bankingsystem.Dto.AuthResponse;
import com.example.bankingsystem.Dto.LoginRequest;
import com.example.bankingsystem.Dto.RegisterRequest;
import com.example.bankingsystem.Entity.Role;
import com.example.bankingsystem.Entity.User;
import com.example.bankingsystem.Repository.UserRepo;
import com.example.bankingsystem.Security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    public String register(RegisterRequest request) {

        // Check if username already exists!
        if(repo.findByUsername(
                request.getUsername()).isPresent()) {
            throw new RuntimeException(
                    "Username already exists!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));
        user.setRole(
                Role.valueOf(
                        request.getRole()
                                .toUpperCase()));
        //                ↑ handles lowercase too!

        repo.save(user);
        return "User registered successfully!";
    }

    public AuthResponse login(LoginRequest request) {

        User user = repo
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found!"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new RuntimeException(
                    "Invalid password!");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().toString());

        return new AuthResponse(token);
    }

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {

        User user = repo
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found!"));

        return org.springframework.security.core
                .userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}