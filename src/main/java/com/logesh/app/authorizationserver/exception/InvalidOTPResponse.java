package com.logesh.app.authorizationserver.exception;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class InvalidOTPResponse {

    private String password;

    public InvalidOTPResponse() {
        this.password="Invalid OTP. Please Try Again!!!";
    }

    public InvalidOTPResponse(String password) {
        this.password = password;
    }
}
