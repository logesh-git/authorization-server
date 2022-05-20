package com.logesh.app.authorizationserver.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class MfaActivationRequest {

    @NotBlank(message = "username cannot be null")
    private String username;

    @Size(min = 6, max = 6,message = "Token should be 6 digits")
    private String mfaToken;
}
