package com.insta.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insta.entity.Story;

public interface StoryRepo extends JpaRepository<Story, Integer> {

    @Query("SELECT s FROM Story s WHERE s.user.id= :userId")
    List<Story> findAllStoryByUserId(@Param("userId") Integer userId);
}
