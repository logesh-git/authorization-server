package com.logesh.app.authorizationserver.service;


import com.logesh.app.authorizationserver.model.User;
import com.logesh.app.authorizationserver.repository.UserDetailRepository;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class MfaService {


    public static String QR_PREFIX =
            "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";

    private final String APP_NAME="EY ";

    @Autowired
    UserDetailRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;


    public boolean isEnabled(String username) {
        return userRepository.findByUsername(username).isMfaEnabled();
    }

    public boolean verifyCode(String username, String code) {
        return new Totp(userService.findUserByUsername(username).getSecret()).verify(code);
    }

    public void generateQRUrl(String username) throws UnsupportedEncodingException {
        User user = userService.findUserByUsername(username);
        String qrCode = QR_PREFIX + URLEncoder.encode(String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                APP_NAME, user.getEmail(), user.getSecret(),APP_NAME),
                "UTF-8");
        emailService.sendQRCode(user.getEmail(),qrCode,user.getSecret());
    }



}