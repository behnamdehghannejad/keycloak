package com.example.keycloak.controller;

import com.example.keycloak.dto.LoginRequest;
import com.example.keycloak.dto.LoginResponse;
import com.example.keycloak.service.KeyCloakService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private final KeyCloakService keyCloakService;

    @PostMapping("/by-password")
    public LoginResponse Login (@RequestBody LoginRequest loginRequest) {
        return keyCloakService.login(loginRequest);
    }
}
