package com.insta.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.insta.dto.UserDto;
import com.insta.entity.User;
import com.insta.exception.UserException;
import com.insta.repo.UserRepo;
import com.insta.serviceImpl.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static UserRepo userRepo;

    // Register new User
    @Override
    public User registerUser(User u) throws UserException {
        // Checking for the valid input field
        if (u.getName() == null || u.getUsername() == null || u.getEmail() == null || u.getPassword() == null)
            throw new UserException("Invalid input value");

        // Verifying that no existing user have same email
        Optional<User> isEmail = userRepo.findByEmail(u.getEmail());
        if (isEmail.isPresent())
            throw new UserException("Email is already exist");

        // Verifying that no existing user have same username
        Optional<User> isUsername = userRepo.findByUsername(u.getUsername());
        if (isUsername.isPresent())
            throw new UserException("Username is already taken");

        // Creating new user, storing in the database & return it
        User newUser = User.builder()
                .name(u.getName())
                .username(u.getUsername())
                .email(u.getEmail())
                .password(u.getPassword())
                .build();

        return userRepo.save(newUser);
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
        // Getting Both users
        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        // Converting both into userDto
        UserDto follower = userToDto(reqUser);
        UserDto following = userToDto(followUser);

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
        // Getting Both users
        User reqUser = findUserById(reqUserId);
        User followUser = findUserById(followUserId);

        // Converting both into userDto
        UserDto follower = userToDto(reqUser);
        UserDto following = userToDto(followUser);

        // Removing a following in reqUser
        reqUser.getFollowing().add(following);
        // Removing a follower in followUser
        followUser.getFollower().add(follower);

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

        // Updating name
        if (updatedUser.getName() != null)
            existingUser.setName(updatedUser.getName());

        // Updating username
        if (updatedUser.getUsername() != null)
            existingUser.setUsername(updatedUser.getUsername());

        // Updating email
        if (updatedUser.getEmail() != null)
            existingUser.setEmail(updatedUser.getEmail());

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

    @Override
    public User findUserByProfile(String token) throws UserException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User findUserByUsername(String username) throws UserException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        // TODO Auto-generated method stub
        return null;
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
