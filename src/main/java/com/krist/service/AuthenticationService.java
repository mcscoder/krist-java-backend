package com.krist.service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.krist.dto.common.TokenDto;
import com.krist.dto.user.LoginDto;
import com.krist.dto.user.RegisterDto;
import com.krist.entity.user.PasswordResetOtp;
import com.krist.entity.user.User;
import com.krist.exception.custom.BadRequestException;
import com.krist.exception.custom.ConflictException;

@Service
public class AuthenticationService {
    final private UserService userService;
    final private PasswordResetOtpService passwordResetOtpService;
    final private PasswordEncoder passwordEncoder;
    final private JwtService jwtService;

    public AuthenticationService(PasswordEncoder passwordEncoder, JwtService jwtService,
            UserService userService, PasswordResetOtpService passwordResetOtpService) {
        this.userService = userService;
        this.passwordResetOtpService = passwordResetOtpService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterDto registerDto) {
        if (userService.userRepository.existsByEmail(registerDto.email())) {
            throw new ConflictException("Duplicated Email");
        }

        User newUser = new User();

        newUser.setFirstName(registerDto.firstName());
        newUser.setLastName(registerDto.lastName());
        newUser.setEmail(registerDto.email());
        newUser.setPassword(passwordEncoder.encode(registerDto.password()));

        userService.userRepository.save(newUser);
    }

    public TokenDto login(LoginDto loginDto) {
        User user = userService.findByEmail(loginDto.email());
        if (!passwordEncoder.matches(loginDto.password(), user.getPassword())) {
            throw new BadRequestException("Invalid password");
        }

        String jwt = jwtService.generateAccessToken(user.getId());
        return new TokenDto(jwt);
    }

    public void forgotPassword(String email) {
        User user = userService.findByEmail(email);
        Random random = new Random();

        // Random range: 10.000 -> 99.999
        Integer otp = random.nextInt(90000) + 10000;

        // Expiration 5 min since created
        Date expiryDate = new Date(System.currentTimeMillis() + PasswordResetOtp.EXPIRATION);

        try {
            PasswordResetOtp existingPasswordResetOtp = passwordResetOtpService.findByUser(user);

            // Update the existing entry
            passwordResetOtpService.updatePasswordResetOtp(existingPasswordResetOtp, otp);
        } catch (NoSuchElementException e) {
            // Create a new entry
            PasswordResetOtp newPasswordResetOtp = new PasswordResetOtp(otp, expiryDate, user);

            // Save the new entry
            passwordResetOtpService.passwordResetOtpRepository.save(newPasswordResetOtp);
        }
    }

    public TokenDto otpAuthentication(String email, Integer otp) {
        User user = userService.findByEmail(email);

        passwordResetOtpService.validateOtp(user, otp);

        return new TokenDto(passwordResetOtpService.generatePasswordResetToken(user.getId(), otp));
    }

    public void resetPassword(String token, String password) {
        passwordResetOtpService.resetPassword(token, password);
    }
}
