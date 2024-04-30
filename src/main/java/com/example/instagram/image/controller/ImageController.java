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
        Long userId = getUserIdFromSession(request);
        ImageDTO savedImage = imageService.saveImage(imageDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
    }

    @GetMapping
    public ResponseEntity<?> getAllImages(HttpServletRequest request) {
        Long userId = getUserIdFromSession(request);
        List<ImageDTO> images = imageService.getImagesByUserId(userId);
        return ResponseEntity.ok(images);
    }

    private Long getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user != null ? user.getId() : null;
    }


}