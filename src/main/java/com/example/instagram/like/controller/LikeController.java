package com.example.instagram.like.controller;

import com.example.instagram.like.domain.dto.LikeDTO;
import com.example.instagram.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeDTO> addLike(@RequestParam("userId") Long userId, @RequestParam("imageId") Long imageId) {
        try {
            LikeDTO likeDTO = likeService.addLike(userId, imageId);
            return new ResponseEntity<>(likeDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
