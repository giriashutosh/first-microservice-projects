package com.ashutosh.gateway.apigateway.models;

import lombok.Data;

import java.util.Collection;

@Data
public class AuthResponse {
    private String userId;
    private String accessToken;
    private String refeshToken;
    private long expiresAt;
    private Collection<String> authorities;
}
