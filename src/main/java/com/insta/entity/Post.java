package com.insta.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.insta.dto.UserDto;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
import lombok.NoArgsConstructor;

// @Data
@AllArgsConstructor
@NoArgsConstructor
// @Builder
@Entity
@Table(name = "t_posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String caption;
    @Column(insertable = false, updatable = false)
    private String image;
    private String location;
    private LocalDateTime createdAt;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "user_id")),
            @AttributeOverride(name = "email", column = @Column(name = "user_email"))
    })
    private UserDto user;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @Embedded
    @ElementCollection
    @JoinTable(name = "likedByUsers", joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserDto> likedByUsers = new HashSet<>();

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<UserDto> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<UserDto> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
