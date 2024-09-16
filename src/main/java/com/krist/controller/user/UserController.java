package com.krist.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krist.dto.user.UserOverview;
import com.krist.entity.user.User;
import com.krist.mapper.user.UserMapper;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/profile")
    public ResponseEntity<UserOverview> profile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserOverview userOverview = UserMapper.INSTANCE.toUserOverview(user);

        return ResponseEntity.ok().body(userOverview);
    }
}
