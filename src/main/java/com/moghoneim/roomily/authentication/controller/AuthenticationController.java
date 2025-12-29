package com.moghoneim.roomily.authentication.controller;

import com.moghoneim.roomily.authentication.dto.AuthRequest;
import com.moghoneim.roomily.authentication.dto.AuthResponse;
import com.moghoneim.roomily.authentication.dto.RegisterRequest;
import com.moghoneim.roomily.authentication.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/users/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account Created Successfully!");
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) throws Exception {
        AuthResponse token = authService.authenticate(request);
        return ResponseEntity.ok(token);
    }
}
