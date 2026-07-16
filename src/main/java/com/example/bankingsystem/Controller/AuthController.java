package com.example.bankingsystem.Controller;

import com.example.bankingsystem.Dto.AuthResponse;
import com.example.bankingsystem.Dto.LoginRequest;
import com.example.bankingsystem.Dto.RegisterRequest;
import com.example.bankingsystem.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request)
    {
        return  service.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request)
    {
        return  service.login(request);
    }

}
