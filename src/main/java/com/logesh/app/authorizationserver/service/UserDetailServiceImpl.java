package com.logesh.app.authorizationserver.service;

import com.logesh.app.authorizationserver.model.AuthUserDetail;
import com.logesh.app.authorizationserver.model.User;
import com.logesh.app.authorizationserver.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        User optionalUser = userDetailRepository.findByUsername(name);

        if (optionalUser == null) {
            throw new UsernameNotFoundException("Username or password wrong");
        }

        UserDetails userDetails = new AuthUserDetail(optionalUser);
        new AccountStatusUserDetailsChecker().check(userDetails);
        return userDetails;


    }
}
