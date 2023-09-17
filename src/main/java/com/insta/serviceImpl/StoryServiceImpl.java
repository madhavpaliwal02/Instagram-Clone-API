package com.insta.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insta.dto.UserDto;
import com.insta.entity.Story;
import com.insta.entity.User;
import com.insta.exception.StoryException;
import com.insta.exception.UserException;
import com.insta.repo.StoryRepo;
import com.insta.serviceImpl.service.StoryService;
import com.insta.serviceImpl.service.UserService;

@Service
public class StoryServiceImpl implements StoryService {

    @Autowired
    private StoryRepo storyRepo;

    @Autowired
    private UserService userService;

    @Autowired
    public ModelMapper mapper;

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
