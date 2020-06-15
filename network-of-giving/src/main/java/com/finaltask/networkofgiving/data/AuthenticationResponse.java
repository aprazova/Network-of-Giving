package com.finaltask.networkofgiving.data;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String authenticationToken;
    private String username;

    public AuthenticationResponse(String authenticationToken, String username) {
        this.authenticationToken = authenticationToken;
        this.username = username;
    }
}
