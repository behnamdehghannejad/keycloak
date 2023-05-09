package com.example.keycloak.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class Controller {

    @GetMapping("/user1")
    @RolesAllowed("admin")
    public String testing1(Authentication authentication) {
        Map<String, Object> jwtInfo = getAuthenticationJwtInfo(authentication);
        System.out.println(jwtInfo);

        Set<String> userPrincipal = getUserRole();
        System.out.println(userPrincipal);

        return "OK";
    }

    private static  Map<String, Object> getAuthenticationJwtInfo(Authentication authentication) {
        Jwt principal = (Jwt) authentication.getPrincipal();
        Map<String, Object> claims = principal.getClaims();
        String role = claims.get("roles").toString();
        return claims;
    }

    public Set<String> getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/user2")
    @RolesAllowed({"employee", "admin"})
    public String testing2() {
        return "OK";
    }
}
