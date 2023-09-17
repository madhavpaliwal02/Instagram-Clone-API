package com.insta.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insta.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    public Optional<User> findUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id IN :users")
    public List<User> findAllUserByIds(@Param("users") List<Integer> userIds);

    @Query("SELECT DISTINCT u FROM User u where u.username LIKE %:query%")
    public List<User> findByQuery(@Param("query") String query);
}
