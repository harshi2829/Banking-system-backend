    package com.example.bankingsystem.Controller;

    import com.example.bankingsystem.Dto.AuthResponse;
    import com.example.bankingsystem.Dto.LoginRequest;
    import com.example.bankingsystem.Dto.RegisterRequest;
    import com.example.bankingsystem.Service.AuthService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Auth Controller", description = "APIs for user registration and login")
    @RestController
    @RequestMapping("/auth")
    public class AuthController {

        @Autowired
        private AuthService service;


        @Operation(summary = "Register a new user", description = "Creates a new user account with encrypted password")
        @PostMapping("/register")
        public String register(@RequestBody RegisterRequest request)
        {
            return  service.register(request);
        }


    @Operation(summary = "Login user", description = "Authenticates user and returns JWT token")
    @PostMapping("/login")
        public AuthResponse login(@RequestBody LoginRequest request)
        {
            return  service.login(request);
        }

    }
