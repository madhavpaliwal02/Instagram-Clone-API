package com.insta.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insta.entity.User;
import com.insta.exception.UserException;
import com.insta.repo.UserRepo;
import com.insta.serviceImpl.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;
    private final UserRepo userRepo;

    // SignUp
    @PostMapping("/signup")
    public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException {
        User u = userService.registerUser(user);
        return new ResponseEntity<User>(u, HttpStatus.OK);
    }

    // SignIn
    @GetMapping("/signin")
    public ResponseEntity<User> signInHandler(Authentication auth) throws BadCredentialsException {
        System.out.println("Auth: " + auth.getName());
        Optional<User> opt = userRepo.findUserByEmail(auth.getName());

        System.out.println(opt.get());

        if (opt.isPresent())
            return new ResponseEntity<User>(opt.get(), HttpStatus.ACCEPTED);

        throw new BadCredentialsException("Invalid username or password");
    }

}
