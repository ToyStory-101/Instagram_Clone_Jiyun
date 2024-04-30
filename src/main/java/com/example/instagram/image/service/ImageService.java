package com.example.instagram.image.service;

import com.example.instagram.image.domain.Image;
import com.example.instagram.image.domain.dto.ImageDTO;
import com.example.instagram.image.repository.ImageRepository;
import com.example.instagram.user.domain.User;
import com.example.instagram.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final UserService userService;
    // 이미지 저장
    public ImageDTO saveImage(ImageDTO imageDTO, Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return null;
        }

        Image image = convertToEntity(imageDTO, user);
        Image savedImage = imageRepository.save(image);
        return convertToDTO(savedImage);
    }


    public List<ImageDTO> getImagesByUserId(Long userId) {
        List<Image> images = imageRepository.findAllByUserId(userId);
        return images.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Image convertToEntity(ImageDTO imageDTO, User user) {
        Image image = new Image();
        image.setCaption(imageDTO.getCaption());
        image.setLocation(imageDTO.getLocation()); // location 설정
        image.setImageUrl(imageDTO.getImageUrl()); // imageUrl 설정
        image.setUser(user);
        return image;
    }


    private ImageDTO convertToDTO(Image image) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setCaption(image.getCaption());
        imageDTO.setLocation(image.getLocation()); // location 설정
        imageDTO.setImageUrl(image.getImageUrl()); // imageUrl 설정
        imageDTO.setUsername(image.getUser().getUsername());
        return imageDTO;
    }
}
