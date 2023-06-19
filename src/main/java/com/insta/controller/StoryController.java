package com.insta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insta.entity.Story;
import com.insta.entity.User;
import com.insta.exception.StoryException;
import com.insta.exception.UserException;
import com.insta.serviceImpl.service.StoryService;
import com.insta.serviceImpl.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryService storyService;
    private final UserService userService;

    /* Create Story Handler */
    @PostMapping("/create")
    public ResponseEntity<Story> createStoryHandler(@RequestBody Story story,
            @RequestHeader("Authorization") String token)
            throws UserException {
        User user = userService.findUserByProfile(token);
        Story createdStory = storyService.createStory(story, user.getId());
        return new ResponseEntity<Story>(createdStory, HttpStatus.CREATED);
    }

    /* Find Story By UserId */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Story>> findStoryByUserIdHandler(@PathVariable("userId") Integer userId)
            throws UserException, StoryException {
        List<Story> stories = storyService.findStoryByUserId(userId);
        return new ResponseEntity<List<Story>>(stories, HttpStatus.OK);
    }
}
