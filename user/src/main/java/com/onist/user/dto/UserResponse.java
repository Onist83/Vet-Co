package com.onist.user.dto;

import com.onist.user.model.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private Role role;
    private boolean enabled;
    private boolean mustChangePassword;
}