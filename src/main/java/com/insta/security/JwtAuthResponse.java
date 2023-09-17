package com.insta.security;

import com.insta.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthResponse {
    private String token;
    private User user;
}
