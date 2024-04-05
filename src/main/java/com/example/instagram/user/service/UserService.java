package com.example.instagram.user.service;

import com.example.instagram.user.domain.User;
import com.example.instagram.user.domain.dto.UserRequest;
import com.example.instagram.user.domain.dto.UserResponse;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest) {
        User newUser = new User();
        newUser.setPassword(userRequest.getPassword());
        newUser.setEmail(userRequest.getEmail());
        newUser.setGender(userRequest.getGender());
        newUser.setName(userRequest.getName());
        newUser.setPhone(userRequest.getPhone());
        newUser.setProfileImage(userRequest.getProfileImage());
        newUser.setUsername(userRequest.getUsername());
        newUser.setCreateDate(LocalDateTime.now()); // 현재 시간으로 설정

        User savedUser = userRepository.save(newUser);
        return new UserResponse(savedUser);
    }

    public UserResponse findUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return new UserResponse(user);
        } else {
            return null;
        }
    }

    public List<UserResponse> findAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }
}
