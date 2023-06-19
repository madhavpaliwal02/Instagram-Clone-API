package com.insta.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insta.entity.Comment;
import com.insta.entity.User;
import com.insta.exception.CommentException;
import com.insta.exception.PostException;
import com.insta.exception.UserException;
import com.insta.serviceImpl.service.CommentService;
import com.insta.serviceImpl.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    // private final

    /* Create Comment Handler */
    @PostMapping("/create/{postId}")
    public ResponseEntity<Comment> createCommentHandler(@RequestBody Comment comment,
            @PathVariable("postId") int postId, @RequestHeader("Authorization") String token)
            throws UserException, PostException {
        User user = userService.findUserByProfile(token);
        Comment createdComment = commentService.createComment(comment, postId, user.getId());
        return new ResponseEntity<Comment>(createdComment, HttpStatus.CREATED);
    }

    /* Get comment by commentId */
    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> findCommmentByIdHandler(@PathVariable("commentId") int commentId)
            throws CommentException {
        Comment comment = commentService.findCommentById(commentId);
        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }

    /* Like comment */
    @PutMapping("like/{commentId}")
    public ResponseEntity<Comment> likeCommentHandlEntity(@PathVariable int commentId,
            @RequestHeader("Authorization") String token) throws CommentException, UserException {
        User user = userService.findUserByProfile(token);

        Comment updatedComment = commentService.likeComment(commentId, user.getId());
        return new ResponseEntity<Comment>(updatedComment, HttpStatus.OK);
    }

    /* Unlike comment */
    @PutMapping("unlike/{commentId}")
    public ResponseEntity<Comment> unlikeCommentHandlEntity(@PathVariable int commentId,
            @RequestHeader("Authorization") String token) throws CommentException, UserException {
        User user = userService.findUserByProfile(token);

        Comment updatedComment = commentService.unlikeComment(commentId, user.getId());
        return new ResponseEntity<Comment>(updatedComment, HttpStatus.OK);
    }

}