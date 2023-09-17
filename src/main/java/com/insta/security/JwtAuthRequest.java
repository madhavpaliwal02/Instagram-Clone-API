package com.insta.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthRequest {
    private String username;
    private String password;
}
