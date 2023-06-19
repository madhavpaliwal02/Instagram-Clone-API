package com.insta.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insta.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
