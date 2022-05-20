package com.logesh.app.authorizationserver.controller;


import com.logesh.app.authorizationserver.payload.MfaActivationRequest;
import com.logesh.app.authorizationserver.model.User;
import com.logesh.app.authorizationserver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/service")
public class OTPController {

    @Autowired
    OTPService otpService;

    @Autowired
    UserService userService;

    @Autowired
    MfaService mfaService;

    @Autowired
    EmailService emailService;

    @Autowired
    RequestValidationService requestValidationService;

    @GetMapping("/generateOtp/{username}")
    public ResponseEntity<?> generateOTP(@Valid @PathVariable String username) {


        User user = userService.findUserByUsername(username);
        if (user == null) {
            return new ResponseEntity<String>("Username doesn't exist", HttpStatus.BAD_REQUEST);
        } else {
            String otp = otpService.generateOTP(username);
            if (otp != null) {
                boolean mailStatus = false;
                try {
                    emailService.sendOtpMessage(Integer.parseInt(otp), user.getEmail());
                    //smsService.send(Integer.parseInt(otp),user.getPhoneNumber());
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

            }
            return ResponseEntity.ok("OTP has been sent successfully to your registered Email Address and Phone Number");
        }
    }

    @GetMapping("/generateQR/{username}")
    public void confirmRegistration(@PathVariable String username) throws UnsupportedEncodingException {
        mfaService.generateQRUrl(username);
    }

    @PostMapping("/activateMfa")
    public ResponseEntity<?> activateMfa(@Valid @RequestBody MfaActivationRequest mfaActivationRequest, BindingResult result) {

        ResponseEntity<?> errorMap = requestValidationService.mapRequestValidation(result);
        if (errorMap != null)
            return errorMap;

        String res=userService.enableMfa(mfaActivationRequest.getUsername(),mfaActivationRequest.getMfaToken());
        return new ResponseEntity<String>(res,HttpStatus.ACCEPTED);
    }



}
