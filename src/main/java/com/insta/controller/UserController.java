package com.insta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@CrossOrigin(origins = "http://localhost:3000")
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
    @PutMapping("/update")
    public ResponseEntity<User> updateUserHandler(@RequestBody User user, @RequestHeader("Authorization") String token)
            throws UserException {
        User reqUser = userService.findUserByProfile(token);
        User updatedUser = userService.updateUserDetails(user, reqUser);
        return new ResponseEntity<User>(updatedUser, HttpStatus.ACCEPTED);
    }

    // Follow a user
    @PutMapping("/follow/{followUserId}")
    public ResponseEntity<MessageResponse> followUserHandler(@PathVariable("followUserId") Integer followUserId,
            @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserByProfile(token);

        String msg = userService.followUser(user.getId(), followUserId);

        MessageResponse res = new MessageResponse(msg);
        return new ResponseEntity<MessageResponse>(res, HttpStatus.OK);
    }

    // Unfollow a user
    @PutMapping("/unfollow/{unfollowUserId}")
    public ResponseEntity<MessageResponse> unfollowUserHandler(@PathVariable("unfollowUserId") Integer unfollowUserId,
            @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserByProfile(token);

        String msg = userService.unfollowUser(user.getId(), unfollowUserId);

        MessageResponse res = new MessageResponse(msg);
        return new ResponseEntity<MessageResponse>(res, HttpStatus.OK);
    }

    // Get a user's profile
    @GetMapping("/req")
    public ResponseEntity<User> findUserProfileHandler(@RequestHeader("Authorization") String token)
            throws UserException {
        User user = userService.findUserByProfile(token);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> findUserByEmailHandler(@PathVariable("email") String email) throws Exception{
        User user = userService.findUserByEmail(email);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
