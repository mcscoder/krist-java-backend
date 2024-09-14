package com.krist.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.krist.entity.user.User;
import com.krist.exception.custom.NotFoundException;
import com.krist.repository.user.UserRepository;

@Service
public class UserService {
    final public UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return user;
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email not found"));

        return user;
    }

    public void updatePassword(Long userId, String rawPassword) {
        User user = findById(userId);

        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
    }
}
