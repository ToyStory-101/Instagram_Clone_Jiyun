package com.example.instagram.like.service;

import com.example.instagram.like.domain.Likes;
import com.example.instagram.like.repository.LikeRepository;
import com.example.instagram.like.domain.dto.LikeDTO;
import com.example.instagram.image.repository.ImageRepository;
import com.example.instagram.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public LikeDTO addLike(Long userId, Long imageId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        var image = imageRepository.findById(imageId).orElseThrow(() -> new IllegalArgumentException("Image not found"));

        var like = Likes.builder()
                .user(user)
                .image(image)
                .build();

        like = likeRepository.save(like);
        return convertToDTO(like);
    }

    private LikeDTO convertToDTO(Likes like) {
        LikeDTO dto = new LikeDTO();
        dto.setId(like.getId());
        dto.setUserId(like.getUser().getId());
        dto.setImageId(like.getImage().getId());
        dto.setCreateDate(like.getCreateDate());
        return dto;
    }
}
