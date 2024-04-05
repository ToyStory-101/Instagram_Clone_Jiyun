package com.example.instagram.user.service;

import com.example.instagram.user.domain.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) { //회원가입(생성)
        return userRepository.save(user);
    }

    public List<User> getAllUsers() { //회원 전체 조회
        return userRepository.findAll();
    }
}

