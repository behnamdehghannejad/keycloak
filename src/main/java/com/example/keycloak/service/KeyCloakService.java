package com.example.keycloak.service;

import com.example.keycloak.dto.LoginRequest;
import com.example.keycloak.dto.LoginResponse;
import com.example.keycloak.util.ConvertUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeyCloakService {

    @Value("${keycloak.client.id}")
    private String clientId;

    @Value("${keycloak.client.secret}")
    private String clientSecret;

    @Value("${keycloak.link.login}")
    private String loginLink;

    private final RestTemplate restTemplate;

    public LoginResponse login(LoginRequest loginRequest) {

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username",loginRequest.getUsername());
        requestBody.add("password",loginRequest.getPassword());
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret",clientSecret);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, headers);

        String keycloakTokenInfo = restTemplate.exchange(loginLink,
                HttpMethod.POST,
                requestEntity,
                String.class
        ).getBody();

        return convertJsonToLoginResponse(keycloakTokenInfo);
    }

    private LoginResponse convertJsonToLoginResponse(String keycloakTokenInfo) {
        Map<String, String> tokenInfoMap = ConvertUtil.convertJsonToMap(keycloakTokenInfo);

        if (tokenInfoMap.isEmpty()) {
            throw new RuntimeException("can not find token info");
        }

        return LoginResponse.builder()
                .accessToken(tokenInfoMap.get("access_token"))
                .expiresIn(tokenInfoMap.get("expires_in"))
                .refreshExpiresIn(tokenInfoMap.get("refresh_expires_in"))
                .refreshToken(tokenInfoMap.get("refresh_token"))
                .tokenType(tokenInfoMap.get("token_type"))
                .notBeforePolicy(tokenInfoMap.get("not-before-policy"))
                .sessionState(tokenInfoMap.get("session_state"))
                .scope(tokenInfoMap.get("scope"))
                .build();
    }
}
