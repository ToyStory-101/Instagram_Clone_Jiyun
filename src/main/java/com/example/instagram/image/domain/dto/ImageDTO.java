package com.example.instagram.image.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ImageDTO {
    private Long id;
    private String caption;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;
    private String location;
    private String imageUrl;
    private String username; // 사용자 이름

    public ImageDTO() {
    }

    public ImageDTO(Long id, String caption, LocalDateTime createDate, String location, String imageUrl, String username) {
        this.id = id;
        this.caption = caption;
        this.createDate = createDate;
        this.location = location;
        this.imageUrl = imageUrl;
        this.username = username;
    }
}


