package com.example.instagram.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment (기본키)
    private Long id;

    @Column
    private String createDate; //자료형 확인

    @Column
    private String email;

    @Column
    private String gender;

    @Column
    private String name; //자료형 확인

    @Column
    private String password;

    @Column
    private String phone;

    @Column
    private String profileImage; //자료형 확인

    @Column
    private String username;

}
