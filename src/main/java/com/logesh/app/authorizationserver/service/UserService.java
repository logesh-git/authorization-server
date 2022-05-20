package com.logesh.app.authorizationserver.service;

import com.logesh.app.authorizationserver.model.User;
import com.logesh.app.authorizationserver.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class UserService {



    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    MfaService mfaService;


    public User findUserByUsername(String username){
        return userDetailRepository.findByUsername(username);
    }


    public String enableMfa(String username,String totp){
       boolean isMfaEnabled= mfaService.verifyCode(username,totp);
       if(isMfaEnabled){
           User user=userDetailRepository.findByUsername(username);
           user.setMfaEnabled(true);
           userDetailRepository.save(user);
       }
       return "Multi-Factor Authentication enabled Successfully";
    }


}
