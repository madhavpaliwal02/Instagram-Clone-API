package com.insta.serviceImpl.service;

import java.util.List;

import com.insta.entity.Post;
import com.insta.exception.PostException;
import com.insta.exception.UserException;

public interface PostService {

    public Post createPost(Post post, Integer userId) throws UserException;

    public String deletePost(Integer postId, Integer userId) throws UserException, PostException;

    public List<Post> findPostsByUserId(Integer userId) throws UserException;

    public Post findPostById(Integer postId) throws PostException;

    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException;

    public String savedPost(Integer postId, Integer userId) throws PostException, UserException;

    public String unsavedPost(Integer postId, Integer userId) throws PostException, UserException;

    public Post likePost(Integer postId, Integer userId) throws UserException, PostException;

    public Post unlikePost(Integer postId, Integer userId) throws UserException, PostException;

}
