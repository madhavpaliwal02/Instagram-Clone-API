package com.insta.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insta.dto.UserDto;
import com.insta.entity.Post;
import com.insta.entity.User;
import com.insta.exception.PostException;
import com.insta.exception.UserException;
import com.insta.repo.PostRepo;
import com.insta.repo.UserRepo;
import com.insta.serviceImpl.service.PostService;
import com.insta.serviceImpl.service.UserService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    public ModelMapper mapper;

    // Create a Post
    @Override
    public Post createPost(Post post, Integer userId) throws UserException {
        User user = userService.findUserById(userId);

        UserDto userDto = userToDto(user);

        post.setUser(userDto);
        post.setCreatedAt(LocalDateTime.now());

        Post postCreated = postRepo.save(post);
        return postCreated;
    }

    // Delete a Post
    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (post.getUser().getId().equals(user.getId())) {
            postRepo.deleteById(postId);
            return "Post Deleted Successfully...";
        }
        throw new PostException("You are not authorized to delete this post");
    }

    // Find Posts by the userId
    @Override
    public List<Post> findPostsByUserId(Integer userId) throws UserException {
        List<Post> posts = postRepo.findPostsByUserId(userId);
        if (posts.size() == 0)
            throw new UserException("User does not have any posts");
        return posts;
    }

    // Find a post by postId
    @Override
    public Post findPostById(Integer postId) throws PostException {
        Optional<Post> post = postRepo.findById(postId);
        if (post.isPresent())
            return post.get();

        throw new PostException("Post not found with the given postId");
    }

    // Find all posts by userIds
    @Override
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException {
        List<Post> posts = postRepo.findAllPostByUserIds(userIds);

        if (posts.size() == 0)
            throw new PostException("Not post available");

        return posts;
    }

    // Saved Post
    @Override
    public String savedPost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        if (!user.getSavedPost().contains(post)) {
            user.getSavedPost().add(post);
            userRepo.save(user);
            return "Post Saved Successfully";
        }
        return "Post already saved";
    }

    // Unsaved Post
    @Override
    public String unsavedPost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (user.getSavedPost().contains(post)) {
            user.getSavedPost().remove(post);
            userRepo.save(user);
            return "Unsaved Post Successfully";
        }
        return "Post not found for unsaved";
    }

    // Like post
    @Override
    public Post likePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        UserDto userDto = userToDto(user);

        if (!post.getLikedByUsers().contains(userDto)) {
            post.getLikedByUsers().add(userDto);
            return postRepo.save(post);
        }
        return post;
    }

    // Unlike post
    @Override
    public Post unlikePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        UserDto userDto = userToDto(user);

        if (post.getLikedByUsers().contains(userDto)) {
            post.getLikedByUsers().remove(userDto);
            return postRepo.save(post);
        }
        return post;
    }

    ////////////////////////////////////////////////////////////////////////////////

    // Helper function map User to userDto
    public UserDto userToDto(User user) {
        return this.mapper.map(user, UserDto.class);
        // UserDto.builder()
        // .id(user.getId())
        // .name(user.getName())
        // .username(user.getUsername())
        // .email(user.getEmail())
        // .image(user.getImage())
        // .build();
    }
}
