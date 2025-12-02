package com.example.BirthCertificate.dto;

public class JwtResponse {
    String toekn;
    int status;

    public JwtResponse() {
    }

    public JwtResponse(String toekn, int status) {
        this.toekn = toekn;
        this.status = status;
    }

    public String getToekn() {
        return toekn;
    }

    public void setToekn(String toekn) {
        this.toekn = toekn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
