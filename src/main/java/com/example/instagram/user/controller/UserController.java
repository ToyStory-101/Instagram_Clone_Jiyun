package com.example.instagram.user.controller;

import com.example.instagram.user.domain.dto.UserRequest;
import com.example.instagram.user.domain.dto.UserResponse;
import com.example.instagram.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class UserController {

    private final UserService userService;

    @PostMapping("/join") // 회원가입
    public ResponseEntity<UserResponse> joinMember(@RequestBody UserRequest userRequest) {
        UserResponse createdUser = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/findOne/{userId}") // 특정 회원 조회
    public ResponseEntity<UserResponse> findMemberById(@PathVariable("userId") Long userId) {
        UserResponse foundUser = userService.findUserById(userId);
        if (foundUser != null) {
            return ResponseEntity.ok(foundUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponse>> findAllMembers() {
        List<UserResponse> allUsers = userService.findAllUsers();
        if (!allUsers.isEmpty()) {
            return ResponseEntity.ok(allUsers);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
