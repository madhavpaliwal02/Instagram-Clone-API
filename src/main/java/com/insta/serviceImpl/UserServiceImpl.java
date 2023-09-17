package com.insta.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.insta.dto.UserDto;
import com.insta.entity.User;
import com.insta.exception.UserException;
import com.insta.repo.UserRepo;
import com.insta.security.JwtTokenHelper;
import com.insta.serviceImpl.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    // Register new User
    @Override
    public User registerUser(User u) throws UserException {
        System.out.println("User: " + u);
        // Checking for the valid input field
        if (u.getName() == null || u.getUsername() == null || u.getPassword() == null)
            throw new UserException("Invalid input value");

        // Verifying that no existing user have same username
        Optional<User> isUsername = userRepo.findUserByUsername(u.getUsername());
        if (isUsername.isPresent())
            throw new UserException("Username is already taken");

        User newUser = new User();
        newUser.setName(u.getName());
        newUser.setUsername(u.getUsername());
        newUser.setPassword(passwordEncoder.encode(u.getPassword()));
        newUser.setImage("default.png");
        // newUser.setPassword(u.getPassword());
        System.out.println("Registered Successfully...");
        return userRepo.save(newUser);
    }

    // Get all Users
    public List<User> getAllUsers() {
        return this.userRepo.findAll();
    }

    // Get a user by userId
    @Override
    public User findUserById(Integer id) throws UserException {
        Optional<User> u = userRepo.findById(id);
        if (u.isPresent())
            return u.get();
        throw new UserException("User exists with userId: " + id);
    }

    // Following a user
    @Override
    public String followUser(Integer reqUserId, Integer followUserId) throws UserException {
        if (reqUserId == followUserId)
            throw new UserException("User can't follow themselves");

        // Getting Both users
        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        // Converting both into userDto
        UserDto follower = userToDto(reqUser);
        UserDto following = userToDto(followUser);

        // Check if already Unfollow
        if (reqUser.getFollowing().contains(following) ||
                followUser.getFollower().contains(follower))
            throw new UserException("You already followed " + followUser.getUsername());

        // Adding a following in reqUser
        reqUser.getFollowing().add(following);
        // Adding a follower in followUser
        followUser.getFollower().add(follower);

        // Saving into the database
        userRepo.save(reqUser);
        userRepo.save(followUser);

        return "You are now following " + followUser.getUsername();
    }

    // Unfollowing a user
    @Override
    public String unfollowUser(Integer reqUserId, Integer followUserId) throws UserException {
        if (reqUserId == followUserId)
            throw new UserException("User can't unfollow themselves");

        // Getting Both users
        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        // Converting both into userDto
        UserDto follower = userToDto(reqUser);
        UserDto following = userToDto(followUser);

        // Check if already Unfollow
        if (!(reqUser.getFollowing().contains(following) &&
                followUser.getFollower().contains(follower)))
            throw new UserException("You already unfollowed " + followUser.getUsername());

        // Removing a following in reqUser
        reqUser.getFollowing().remove(following);
        // Removing a follower in followUser
        followUser.getFollower().remove(follower);

        // Saving into the database
        userRepo.save(reqUser);
        userRepo.save(followUser);

        return "You are now unfollowing " + followUser.getUsername();
    }

    // Find users by ids
    @Override
    public List<User> findUserByIds(List<Integer> uids) throws UserException {
        List<User> users = userRepo.findAllUserByIds(uids);
        if (users.size() == 0)
            throw new UserException("No users found");
        return users;
    }

    // Updating a user
    @Override
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {

        System.out.println(updatedUser);
        System.out.println(existingUser);
        // Updating name
        if (updatedUser.getName() != null)
            existingUser.setName(updatedUser.getName());

        // Updating username
        if (updatedUser.getUsername() != null)
            existingUser.setUsername(updatedUser.getUsername());

        // Updating password
        if (updatedUser.getPassword() != null)
            existingUser.setPassword(updatedUser.getPassword());

        // Updating mobile
        if (updatedUser.getMobile() != null)
            existingUser.setMobile(updatedUser.getMobile());

        // Updating website
        if (updatedUser.getWebsite() != null)
            existingUser.setWebsite(updatedUser.getWebsite());

        // Updating bio
        if (updatedUser.getBio() != null)
            existingUser.setBio(updatedUser.getBio());

        // Updating gender
        if (updatedUser.getGender() != null)
            existingUser.setGender(updatedUser.getGender());

        // Updating image
        if (updatedUser.getImage() != null)
            existingUser.setImage(updatedUser.getImage());

        // Saving & Returning
        if (updatedUser.getId().equals(existingUser.getId()))
            return userRepo.save(existingUser);

        throw new UserException("You can't update this user");
    }

    // Get a User by username
    @Override
    public User findUserByUsername(String username) throws UserException {
        Optional<User> user = userRepo.findUserByUsername(username);
        if (user.isPresent())
            return user.get();
        throw new UserException("User not found with username " + username);
    }

    // Find User by Profile
    @Override
    public User findUserByProfile(String token) throws UserException {
        // Bearer uyfydtrdsyhjhjhugtfrdrtedfggjkoiuipgcfdxtrdtghgvc
        token = token.substring(7);

        // String username =
        // SecurityContextHolder.getContext().getAuthentication().getName();
        String username = this.jwtTokenHelper.getUsernameFromToken(token);
        System.out.println(username);
        Optional<User> user = this.userRepo.findUserByUsername(username);
        return user.get();

    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        return null;
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
