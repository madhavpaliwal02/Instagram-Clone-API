package com.insta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.insta.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.insta.entity.User> opt = userRepo.findUserByEmail(username);

        if (opt.isPresent()) {
            com.insta.entity.User user = opt.get();

            List<GrantedAuthority> authorities = new ArrayList<>();

            return new User(user.getEmail(), user.getPassword(), authorities);
        }
        throw new BadCredentialsException("User not found with username: " + username);
    }

}
