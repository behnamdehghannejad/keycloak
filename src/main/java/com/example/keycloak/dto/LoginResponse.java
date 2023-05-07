package com.example.keycloak.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String expiresIn;
    private String refreshExpiresIn;
    private String refreshToken;
    private String tokenType;
    private String notBeforePolicy;
    private String sessionState;
    private String scope;
}
