package com.example.instagram.image.domain;

import com.example.instagram.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String caption;

    @CreatedDate
    @Column(name = "createDate", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;

    //게시물 등록할때 시간 설정
    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
    }

    @Column
    private String location;

    // 지연로딩 할때 게시물 조회가 제대로 되지 않아 즉시 로딩으로 변경
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @Column
    private String imageUrl;
}
