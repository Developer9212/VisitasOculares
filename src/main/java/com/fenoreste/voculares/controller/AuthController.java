package com.fenoreste.voculares.controller;

import com.fenoreste.voculares.security.AuthService;
import com.fenoreste.voculares.security.LoginRequestVo;
import com.fenoreste.voculares.security.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestVo request) {

        TokenResponse token = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        return ResponseEntity.ok(authService.login(request.getUsername(), request.getPassword()));
    }
}