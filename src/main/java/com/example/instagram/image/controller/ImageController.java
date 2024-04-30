package com.example.instagram.image.controller;

import com.example.instagram.image.domain.Image;
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

    private User getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute("user");
    }

    private ResponseEntity<?> checkUserAuthentication(User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<?> createImage(@RequestBody Image image, HttpServletRequest request) {
        User user = getSessionUser(request);
        ResponseEntity<?> authResponse = checkUserAuthentication(user);
        if (authResponse != null) return authResponse;

        image.setUser(user);  // 이 부분을 수정완료 (의존성에서 오류났었음)
        Image savedImage = imageService.saveImage(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
    }

    @GetMapping
    public ResponseEntity<?> getAllImages(HttpServletRequest request) {
        User user = getSessionUser(request);
        ResponseEntity<?> authResponse = checkUserAuthentication(user);
        if (authResponse != null) return authResponse;

        List<Image> images = imageService.getImagesByUserId(user.getId());
        return ResponseEntity.ok(images);
    }
}
