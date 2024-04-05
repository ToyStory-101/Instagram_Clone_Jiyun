package com.example.instagram.user.domain.dto;

import com.example.instagram.user.domain.User;
import lombok.*;
import java.time.LocalDateTime;

@Getter
public class UserResponse {

    private Long id;
    private String password;
    private LocalDateTime createDate;
    private String email;
    private String gender;
    private String name;
    private String phone;
    private String profileImage;
    private String username;

    public UserResponse (User user) {
            this.id = user.getId();
            this.password = user.getPassword();
            this.createDate = user.getCreateDate();
            this.email = user.getEmail();
            this.gender = user.getGender();
            this.name = user.getName();
            this.phone = user.getPhone();
            this.profileImage = user.getProfileImage();
            this.username = user.getUsername();
    }
}
