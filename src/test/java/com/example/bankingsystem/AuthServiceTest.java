package com.example.bankingsystem;

import com.example.bankingsystem.Dto.AuthResponse;
import com.example.bankingsystem.Dto.LoginRequest;
import com.example.bankingsystem.Dto.RegisterRequest;
import com.example.bankingsystem.Entity.Role;
import com.example.bankingsystem.Entity.User;
import com.example.bankingsystem.Repository.UserRepo;
import com.example.bankingsystem.Security.JWTUtil;
import com.example.bankingsystem.Service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepo repo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    private User user;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("harshini");
        user.setPassword("encodedPassword");
        user.setRole(Role.CUSTOMER);

        registerRequest = new RegisterRequest();
        registerRequest.setUsername("harshini");
        registerRequest.setPassword("password123");
        registerRequest.setRole("customer");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("harshini");
        loginRequest.setPassword("password123");
    }

    // ==================== REGISTER TESTS ====================

    @Test
    void register_Success() {
        when(repo.findByUsername("harshini")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        String result = authService.register(registerRequest);

        assertEquals("User registered successfully!", result);
        verify(repo).save(any(User.class));
    }

    @Test
    void register_UsernameAlreadyExists_ThrowsException() {
        when(repo.findByUsername("harshini")).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.register(registerRequest));

        assertEquals("Username already exists!", exception.getMessage());
        verify(repo, never()).save(any());
    }

    // ==================== LOGIN TESTS ====================

    @Test
    void login_Success() {
        when(repo.findByUsername("harshini")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken("harshini", "CUSTOMER")).thenReturn("mockToken");

        AuthResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("mockToken", response.getToken());
    }

    @Test
    void login_UserNotFound_ThrowsException() {
        when(repo.findByUsername("harshini")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.login(loginRequest));

        assertEquals("User not found!", exception.getMessage());
    }

    @Test
    void login_InvalidPassword_ThrowsException() {
        when(repo.findByUsername("harshini")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.login(loginRequest));

        assertEquals("Invalid password!", exception.getMessage());
    }

    // ==================== LOAD USER TESTS ====================

    @Test
    void loadUserByUsername_Success() {
        when(repo.findByUsername("harshini")).thenReturn(Optional.of(user));

        UserDetails userDetails = authService.loadUserByUsername("harshini");

        assertNotNull(userDetails);
        assertEquals("harshini", userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        when(repo.findByUsername("harshini")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> authService.loadUserByUsername("harshini"));
    }
}