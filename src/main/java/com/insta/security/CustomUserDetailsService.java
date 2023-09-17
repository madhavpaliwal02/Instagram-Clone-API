package com.insta.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.insta.entity.User;
import com.insta.exception.UserException;
import com.insta.repo.UserRepo;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    public UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        try {
            user = this.userRepo.findUserByUsername(username)
                    .orElseThrow(() -> new UserException("User not found with given username : " + username));
        } catch (UserException e) {
            e.printStackTrace();
        }
        return user;
    }

}
