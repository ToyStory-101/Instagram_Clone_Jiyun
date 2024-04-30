package com.example.instagram.image.service;

import com.example.instagram.image.domain.Image;
import com.example.instagram.image.domain.dto.ImageDTO;
import com.example.instagram.image.repository.ImageRepository;
import com.example.instagram.user.domain.User;
import com.example.instagram.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final UserService userService;

    //게시물 등록
    public ImageDTO saveImage(ImageDTO imageDTO, Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return null;
        }

        Image image = convertToEntity(imageDTO, user);
        Image savedImage = imageRepository.save(image);
        return convertToDTO(savedImage);
    }

    //게시물 조회
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

    public Long getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user != null ? user.getId() : null;
    }


    //게시물 삭제
    public boolean deleteImage(Long id, Long userId){
        Optional<Image> optionalImage = imageRepository.findByIdAndUserId(id, userId);

        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();
            imageRepository.delete(image);
            return true; // 삭제 성공
        } else {
            return false; // 삭제 실패 (게시물이 없거나 해당 사용자가 작성자가 아님)
        }
    }

    public boolean updateImage(Long id, ImageDTO updatedImageDTO, Long userId) {
        Optional<Image> optionalImage = imageRepository.findByIdAndUserId(id, userId);
        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();
            // 게시물 수정
            image.setCaption(updatedImageDTO.getCaption());
            image.setLocation(updatedImageDTO.getLocation());
            image.setImageUrl(updatedImageDTO.getImageUrl());

            imageRepository.save(image);
            return true;
        }
        return false;
    }


}
