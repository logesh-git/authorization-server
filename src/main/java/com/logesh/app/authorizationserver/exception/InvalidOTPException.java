package com.logesh.app.authorizationserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidOTPException extends  RuntimeException{

    public InvalidOTPException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

}
