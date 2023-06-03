package com.insta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insta.dto.MessageResponse;
import com.insta.entity.User;
import com.insta.exception.UserException;
import com.insta.serviceImpl.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String home() {
        return "Hii Palak...";
    }

    // Get a user by userId
    @GetMapping("/id/{id}")
    public ResponseEntity<User> findUserByIdHandler(@PathVariable("id") Integer id) throws UserException {
        User user = userService.findUserById(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // Get a user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<User> findUserByUsernameHandler(@PathVariable("username") String username)
            throws UserException {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // Get existing users by userIds
    @GetMapping("/userIds/{userIds}")
    public ResponseEntity<List<User>> findUsersByUserIdsHandler(@PathVariable("userIds") List<Integer> userIds)
            throws UserException {
        List<User> list = userService.findUserByIds(userIds);
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
    }

    // Search a user
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserHandler(@RequestParam("q") String query) throws UserException {
        List<User> list = userService.searchUser(query);
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
    }

    // Update a user
    // @PutMapping()
    // public ResponseEntity<User> updateUserHandler(@RequestHeader("Authorization")
    // String token) {
    // return null;
    // }

    // Follow a user
    // @PutMapping("/follow/{followUserId}")
    // public ResponseEntity<MessageResponse> followUserHandler() {
    // return null;
    // }

    // Unfollow a user
    // @PutMapping("/unfollow/{unfollowUserId}")
    // public ResponseEntity<MessageResponse> unfollowUserHandler() {
    // return null;
    // }

    // Get a user's profile
    // @GetMapping("/req")
    // public ResponseEntity<MessageResponse>
    // findUserProfileHandler(@RequestHeader("Authorization")){
    // return null;
    // }

}