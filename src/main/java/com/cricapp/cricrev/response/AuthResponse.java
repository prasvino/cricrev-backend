package com.cricapp.cricrev.response;

public class AuthResponse {
    private String token;

    public AuthResponse(String token,String message) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
