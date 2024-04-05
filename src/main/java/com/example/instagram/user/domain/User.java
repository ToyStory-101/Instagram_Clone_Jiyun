package com.example.instagram.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment (기본키)
    private Long id;

    @CreatedDate
    @Column(name = "createDate", updatable = false) //createDate로 설정 했는데 컬럼명이 create_date로 생김
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;

    @Column
    private String email;

    @Column
    private String gender;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String phone;

    @Column
    private String profileImage;

    @Column
    private String username;

}
