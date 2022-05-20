package com.logesh.app.authorizationserver.service;

import com.logesh.app.authorizationserver.util.OTPTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {


    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpMessage(int otp, String to) throws MessagingException {

        String subject = "Login OTP - " + String.valueOf(otp);
        //Generate The Template to send OTP
        OTPTemplate template = new OTPTemplate("EmailOtpTemplate.html");
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("user", to);
        replacements.put("otpnum", String.valueOf(otp));
        String message = template.getTemplate(replacements);

        sendEmail(message, to, subject);
    }

    public void sendQRCode(String to,String QRUrl, String secret) {

        String subject = "Scan and Verify Multi-Factor Authentication";
        //Generate The Template to send OTP
        OTPTemplate template = new OTPTemplate("EmailQRTemplate.html");
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("user", to);
        replacements.put("url", QRUrl);
        replacements.put("secret",secret);
        String message = template.getTemplate(replacements);

        sendEmail(message, to, subject);
    }

    protected void sendEmail(String message, String to, String subject) {

        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);
            javaMailSender.send(msg);
        }
        catch (MessagingException e){
            throw new RuntimeException("Something went wrong while constructing Email Body");
        }
    }

}