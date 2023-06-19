package com.insta.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.insta.dto.UserDto;
import com.insta.entity.Comment;
import com.insta.entity.Post;
import com.insta.entity.User;
import com.insta.exception.CommentException;
import com.insta.exception.PostException;
import com.insta.exception.UserException;
import com.insta.repo.CommentRepo;
import com.insta.repo.PostRepo;
import com.insta.serviceImpl.service.CommentService;
import com.insta.serviceImpl.service.PostService;
import com.insta.serviceImpl.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final UserService userService;
    private final PostService postService;
    private final PostRepo postRepo;

    /* Creating a comment */
    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException {
        // Fetching user & post
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        // Mapoing User to UserDto
        UserDto userDto = userToDto(user);

        // Setting up Comment & Saving
        comment.setUser(userDto);
        comment.setCreatedAt(LocalDateTime.now());
        Comment createdComment = commentRepo.save(comment);

        // Setting up Post & saving
        post.getComments().add(createdComment);
        postRepo.save(post);

        return createdComment;
    }

    /* Find comment by Id */
    @Override
    public Comment findCommentById(Integer commentId) throws CommentException {
        Optional<Comment> comment = commentRepo.findById(commentId);
        if (comment.isPresent())
            return comment.get();
        throw new CommentException("Comment not exist with id: " + commentId);
    }

    /* Like a Comment */
    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException {
        // Fetching user & post
        User user = userService.findUserById(userId);
        Comment comment = this.findCommentById(commentId);

        // Mapping User to UserDto
        UserDto userDto = userToDto(user);

        // Setting up like & saving
        comment.getLikedByUsers().add(userDto);
        Comment updatedComment = commentRepo.save(comment);

        return updatedComment;
    }

    /* Unlike a comment */
    @Override
    public Comment unlikeComment(Integer commentId, Integer userId) throws CommentException, UserException {
        // Fetching user & post
        User user = userService.findUserById(userId);
        Comment comment = this.findCommentById(commentId);

        // Mapping User to UserDto
        UserDto userDto = userToDto(user);

        // Setting up like & saving
        comment.getLikedByUsers().remove(userDto);
        Comment updatedComment = commentRepo.save(comment);

        return updatedComment;
    }

    ////////////////////////////////////////////////////////////////////////////////

    // Helper function map User to userDto
    public UserDto userToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .image(user.getImage())
                .build();
    }

}
