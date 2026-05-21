package org.taj.hotel.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.taj.hotel.service.AppUserDetailService;
import org.taj.hotel.service.JWTService;
import org.taj.hotel.view.LoginRequest;
import org.taj.hotel.view.UserRegistrationRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final AppUserDetailService appUserDetailService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthController(
            AppUserDetailService appUserDetailService,
            AuthenticationManager authenticationManager,
            JWTService jwtService
    ) {
        this.appUserDetailService = appUserDetailService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody UserRegistrationRequest request
    ) {

        appUserDetailService.registerUser(request);

        return ResponseEntity.ok("Happy User");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request
    ) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        UserDetails user =
                appUserDetailService.loadUserByUsername(request.username());

        String token = jwtService.sign(user);
        return ResponseEntity.ok(Map.of("token", token));
    }
}