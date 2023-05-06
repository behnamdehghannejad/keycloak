package com.example.keycloak.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class Controller {

    @GetMapping("/user1")
//    @RolesAllowed("admin")
    public String testing1() {
        return "OK";
    }

    @GetMapping("/user2")
//    @RolesAllowed("employee")
    public String testing2() {
        return "OK";
    }
}
