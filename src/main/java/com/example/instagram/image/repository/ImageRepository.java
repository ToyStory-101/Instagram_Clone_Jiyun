package com.example.instagram.image.repository;
import com.example.instagram.image.domain.Image;
import com.example.instagram.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByUserId(Long userId);  // 사용자 ID로 모든 이미지 조회
    Optional<Image> findByIdAndUser_Id(Long id, Long userId);  // 이미지 ID와 사용자 ID로 특정 이미지 조회

    Optional<Image> findByIdAndUserId(Long id, Long userId);
}

