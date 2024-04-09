package com.example.instagram.user.domain.dto;

import lombok.*;

@Getter
@Setter
public class UserUpdateRequest {

    private String name;
    private String email;
    private String gender;
    private String password;
    private String phone;
    private String profileImage;
    private String username;

}

