package com.insta.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insta.dto.MessageResponse;
import com.insta.entity.Post;
import com.insta.entity.User;
import com.insta.exception.PostException;
import com.insta.exception.UserException;
import com.insta.serviceImpl.service.PostService;
import com.insta.serviceImpl.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    // Create post
    @PostMapping("/create")
    public ResponseEntity<Post> createPostHandler(@RequestBody Post post, @RequestHeader("Authorization") String token)
            throws UserException {
        User user = userService.findUserByProfile(token);
        Post createdPost = postService.createPost(post, user.getId());

        return new ResponseEntity<Post>(createdPost, HttpStatus.CREATED);
    }

    // Find a post by postId
    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable("postId") Integer postId) throws PostException {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    // Find all posts by userId
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Post>> findPostsByUserIdHandler(@PathVariable("userId") Integer userId)
            throws UserException {
        List<Post> posts = postService.findPostsByUserId(userId);
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    // Find all posts by user ids
    @GetMapping("/following/{userIds}")
    public ResponseEntity<List<Post>> findAllPostsByUserIdsHandler(@PathVariable("userIds") List<Integer> userIds)
            throws PostException, UserException {
        List<Post> posts = postService.findAllPostByUserIds(userIds);
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    // Like a post
    @PutMapping("/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable("postId") Integer postId,
            @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserByProfile(token);
        Post updatedPost = postService.likePost(postId, user.getId());
        return new ResponseEntity<Post>(updatedPost, HttpStatus.ACCEPTED);
    }

    // Unlike a post
    @PutMapping("/unlike/{postId}")
    public ResponseEntity<Post> unlikePostHandler(@PathVariable("postId") Integer postId,
            @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserByProfile(token);
        Post updatedPost = postService.unlikePost(postId, user.getId());
        return new ResponseEntity<Post>(updatedPost, HttpStatus.ACCEPTED);
    }

    // Delete a post
    @DeleteMapping("delete/{postId}")
    public ResponseEntity<MessageResponse> deletePostHandler(@PathVariable("postId") Integer postId,
            @RequestHeader("Authorization") String token) throws UserException, PostException {

        User user = userService.findUserByProfile(token);
        String msg = postService.deletePost(postId, user.getId());

        MessageResponse msgResp = new MessageResponse(msg);

        return new ResponseEntity<MessageResponse>(msgResp, HttpStatus.OK);
    }

    // Saved a post
    @PutMapping("/saved/{postId}")
    public ResponseEntity<MessageResponse> savedPostHandler(@PathVariable("postId") Integer postId,
            @RequestHeader("Authorization") String token) throws UserException, PostException {

        User user = userService.findUserByProfile(token);
        String msg = postService.savedPost(postId, user.getId());

        MessageResponse msgResp = new MessageResponse(msg);

        return new ResponseEntity<MessageResponse>(msgResp, HttpStatus.OK);
    }

    // Unsaved a post
    @PutMapping("/unsaved/{postId}")
    public ResponseEntity<MessageResponse> unsavedPostHandler(@PathVariable("postId") Integer postId,
            @RequestHeader("Authorization") String token) throws UserException, PostException {

        User user = userService.findUserByProfile(token);
        String msg = postService.unsavedPost(postId, user.getId());

        MessageResponse msgResp = new MessageResponse(msg);

        return new ResponseEntity<MessageResponse>(msgResp, HttpStatus.OK);
    }

}
