package com.example.ArtNew.dtoDataTransferObject;

public class JwtResponse {
    String token;
    int status;

    public JwtResponse() {
    }

    public JwtResponse(String token, int status) {
        this.token = token;
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
