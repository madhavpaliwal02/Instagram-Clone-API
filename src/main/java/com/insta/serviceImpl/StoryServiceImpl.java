package com.insta.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.insta.dto.UserDto;
import com.insta.entity.Story;
import com.insta.entity.User;
import com.insta.exception.StoryException;
import com.insta.exception.UserException;
import com.insta.repo.StoryRepo;
// import com.insta.repo.UserRepo;
import com.insta.serviceImpl.service.StoryService;
import com.insta.serviceImpl.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryRepo storyRepo;
    private final UserService userService;
    // private final UserRepo userRepo;

    /* Create Story */
    @Override
    public Story createStory(Story story, Integer userId) throws UserException {
        // Fetching user
        User user = userService.findUserById(userId);

        // Map to UserDto
        UserDto userDto = userToDto(user);

        // Setting Story
        story.setUser(userDto);
        story.setTimestamp(LocalDateTime.now());
        // Settin User
        user.getStories().add(story);

        return storyRepo.save(story);
    }

    /* Find Story by UserId */
    @Override
    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException {
        // Fetching user
        User user = userService.findUserById(userId);

        // Getting story
        List<Story> stories = user.getStories();

        if (stories.size() == 0)
            throw new StoryException("User doesn't have stories...");

        return stories;
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
