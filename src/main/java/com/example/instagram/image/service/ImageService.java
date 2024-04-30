package com.example.instagram.image.service;

import com.example.instagram.image.domain.Image;
import com.example.instagram.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    // 이미지 저장
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    // 특정 사용자의 모든 이미지 조회
    public List<Image> getImagesByUserId(Long userId) {
        return imageRepository.findAllByUserId(userId);
    }
}
