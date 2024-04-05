package com.example.instagram.user.domain.dto;

import com.example.instagram.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

    private String name;
    private String password;
    private String email;
    private String gender;
    private String phone;
    private String profileImage;
    private String username;

    @Builder
    public UserRequest(String name, String password,String email, String gender, String phone, String profileImage, String username){
        this.name = name;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.profileImage = profileImage;
        this. username = username;
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .gender(gender)
                .gender(phone)
                .profileImage(profileImage)
                .username(username)
                .build();
    }
}
