package com.example.instagram.image.controller;

import com.example.instagram.image.domain.dto.ImageDTO;
import com.example.instagram.image.service.ImageService;
import com.example.instagram.user.domain.User;
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



}