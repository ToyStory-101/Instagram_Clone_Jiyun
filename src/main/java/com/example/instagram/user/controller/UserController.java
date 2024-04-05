package com.example.instagram.user.controller;

import com.example.instagram.user.domain.User;
import com.example.instagram.user.domain.dto.UserRequest;
import com.example.instagram.user.domain.dto.UserResponse;
import com.example.instagram.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class UserController {

    private final UserService userService;

    @PostMapping("/login") // 로그인
    public ResponseEntity<String> login(@RequestBody User loginUser, HttpServletRequest request) {
        User user = userService.loginUser(loginUser.getUsername(), loginUser.getPassword());
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user); // 세션에 사용자 정보 저장
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    @GetMapping("/userinfo") // test 용 : 로그인했을때 세션으로 자신의 정보를 조회하는 컨트롤러
    public ResponseEntity<User> getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

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
