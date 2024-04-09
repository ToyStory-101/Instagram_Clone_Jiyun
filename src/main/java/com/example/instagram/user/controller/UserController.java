package com.example.instagram.user.controller;

import com.example.instagram.user.domain.User;
import com.example.instagram.user.domain.dto.UserRequest;
import com.example.instagram.user.domain.dto.UserResponse;
import com.example.instagram.user.domain.dto.UserUpdateRequest;
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

    @PostMapping("/logout") //로그아웃
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
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

    @GetMapping("/findAll") //전체 회원 조회
    public ResponseEntity<List<UserResponse>> findAllMembers() {
        List<UserResponse> allUsers = userService.findAllUsers();
        if (!allUsers.isEmpty()) {
            return ResponseEntity.ok(allUsers);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete") //(내 정보)회원 탈퇴
    public ResponseEntity<String> deleteUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                userService.deleteUser(user.getId());
                session.removeAttribute("user");
                return ResponseEntity.ok("사용자가 성공적으로 삭제되었습니다.");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션에 사용자 정보가 없거나 권한이 없습니다.");
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                userService.updateUser(user.getId(), userUpdateRequest);

                // 세션의 사용자 정보 업데이트
                user.setName(userUpdateRequest.getName());
                user.setEmail(userUpdateRequest.getEmail());
                user.setGender(userUpdateRequest.getGender());
                user.setPassword(userUpdateRequest.getPassword());
                user.setPhone(userUpdateRequest.getPhone());
                user.setProfileImage(userUpdateRequest.getProfileImage());
                user.setUsername(userUpdateRequest.getUsername());


                session.setAttribute("user", user);

                return ResponseEntity.ok("사용자 정보가 성공적으로 업데이트되었습니다.");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션에 사용자 정보가 없거나 권한이 없습니다.");
    }
}
