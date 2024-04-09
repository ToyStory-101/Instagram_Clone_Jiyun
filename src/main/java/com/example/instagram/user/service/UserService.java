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

    //로그인
    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    //회원가입
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

    //특정 회원 조회
    public UserResponse findUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return new UserResponse(user);
        } else {
            return null;
        }
    }

    //전체 회원 조회
    public List<UserResponse> findAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
