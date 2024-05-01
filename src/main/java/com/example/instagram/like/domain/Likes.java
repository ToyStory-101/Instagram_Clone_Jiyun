package com.example.instagram.like.domain;

import com.example.instagram.image.domain.Image;
import com.example.instagram.user.domain.User;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder // 빌더 패턴 적용
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 가진 생성자 생성
public class Likes {

    @CreatedDate
    @Column(name = "createDate", updatable = false) //createDate로 설정 했는데 컬럼명이 create_date로 생김
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //게시물의 ID
    @JoinColumn(name = "imageId", referencedColumnName = "id")
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY) //좋아요를 누른 사람의 ID
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    //게시물 등록할때 시간 설정
    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
    }


}

