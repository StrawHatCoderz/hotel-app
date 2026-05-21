package org.taj.hotel.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.taj.hotel.service.AppUserDetailService;
import org.taj.hotel.view.UserRegistrationRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AppUserDetailService appUserDetailService;

    public AuthController(AppUserDetailService appUserDetailService) {
        this.appUserDetailService = appUserDetailService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        this.appUserDetailService.registerUser(request);
        return ResponseEntity.ok().build();
    }
}

