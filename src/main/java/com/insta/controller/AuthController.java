package com.insta.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insta.entity.User;
import com.insta.exception.UserException;
import com.insta.serviceImpl.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
// @RequestMapping("/api/users")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException {
        User u = userService.registerUser(user);
        return new ResponseEntity<User>(u, HttpStatus.OK);
    }

}
