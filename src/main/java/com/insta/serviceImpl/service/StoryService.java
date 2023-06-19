package com.insta.serviceImpl.service;

import java.util.List;

import com.insta.entity.Story;
import com.insta.exception.StoryException;
import com.insta.exception.UserException;

public interface StoryService {

    public Story createStory(Story story, Integer userId) throws UserException;

    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException;
}
