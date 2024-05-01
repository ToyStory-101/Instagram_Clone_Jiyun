package com.example.instagram.like.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class LikeDTO {
    private Long id;
    private Long imageId;
    private Long userId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;

    public LikeDTO() {
    }
}

