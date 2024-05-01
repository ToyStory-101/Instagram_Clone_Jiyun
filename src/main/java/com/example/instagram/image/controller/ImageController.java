package com.example.instagram.image.controller;

import com.example.instagram.image.domain.dto.ImageDTO;
import com.example.instagram.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<?> createImage(@RequestBody ImageDTO imageDTO, HttpServletRequest request) {
        Long userId = imageService.getUserIdFromSession(request);
        ImageDTO savedImage = imageService.saveImage(imageDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
    }

    @GetMapping
    public ResponseEntity<?> getAllImages(HttpServletRequest request) {
        Long userId = imageService.getUserIdFromSession(request);
        List<ImageDTO> images = imageService.getImagesByUserId(userId);
        return ResponseEntity.ok(images);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable("id") Long id, HttpServletRequest request) {
        Long userId = imageService.getUserIdFromSession(request);
        boolean deleted = imageService.deleteImage(id, userId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateImage(@PathVariable("id") Long id, @RequestBody ImageDTO updatedImageDTO, HttpServletRequest request) {
        Long userId = imageService.getUserIdFromSession(request);
        // 이미지 ID를 사용하여 이미지를 업데이트하고, 사용자 ID로 인증합니다.
        boolean updated = imageService.updateImage(id, updatedImageDTO, userId);
        if (updated) {
            return ResponseEntity.ok("게시물이 성공적으로 수정되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("해당 게시물을 수정할 수 있는 권한이 없습니다.");
        }
    }

}