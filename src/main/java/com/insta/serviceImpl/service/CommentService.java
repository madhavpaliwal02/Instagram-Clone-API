package com.insta.serviceImpl.service;

import com.insta.entity.Comment;
import com.insta.exception.CommentException;
import com.insta.exception.PostException;
import com.insta.exception.UserException;

public interface CommentService {

    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException;

    public Comment findCommentById(Integer commentId) throws CommentException;

    public Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException;

    public Comment unlikeComment(Integer commentId, Integer userId) throws CommentException, UserException;
}
