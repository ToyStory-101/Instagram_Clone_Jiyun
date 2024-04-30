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

    @Column
    private String location;

    // User 엔티티와의 관계를 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
}
