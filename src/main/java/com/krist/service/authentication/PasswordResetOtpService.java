package com.krist.service.authentication;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.krist.entity.user.PasswordResetOtp;
import com.krist.entity.user.User;
import com.krist.exception.custom.BadRequestException;
import com.krist.exception.custom.NotFoundException;
import com.krist.repository.user.PasswordResetOtpRepository;
import com.krist.service.user.UserService;
import com.krist.util.jwt.JwtUtil;

import io.jsonwebtoken.Claims;

@Service
public class PasswordResetOtpService {
    final private JwtUtil jwtUtil;
    final private UserService userService;
    final public PasswordResetOtpRepository passwordResetOtpRepository;

    public PasswordResetOtpService(JwtUtil jwtUtil, UserService userService,
            PasswordResetOtpRepository passwordResetOtpRepository) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordResetOtpRepository = passwordResetOtpRepository;
    }

    public String generatePasswordResetToken(Long userId, Integer otp) {
        return jwtUtil.createPasswordResetToken(userId, otp);
    }

    public PasswordResetOtp findByUser(User user) {
        return passwordResetOtpRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public PasswordResetOtp findByUserAndOtp(User user, Integer otp) {
        return passwordResetOtpRepository.findByUserAndOtp(user, otp)
                .orElseThrow(() -> new BadRequestException("Incorrect OTP code"));
    }

    public PasswordResetOtp updatePasswordResetOtp(PasswordResetOtp passwordResetOtp, Integer otp) {
        passwordResetOtp.setOtp(otp);
        passwordResetOtp
                .setExpiryDate(new Date(System.currentTimeMillis() + PasswordResetOtp.EXPIRATION));
        return passwordResetOtpRepository.save(passwordResetOtp);
    }

    public void validateOtpExpiry(PasswordResetOtp passwordResetOtp) {
        if (passwordResetOtp.getExpiryDate().before(new Date(System.currentTimeMillis()))) {
            throw new BadRequestException("OTP has been expired");
        }
    }

    public void validatePasswordResetToken(String token) {
        Claims claims = jwtUtil.parseToken(token);

        Long userId = Long.valueOf(claims.getSubject());

        User user = userService.findById(userId);
        Integer otp = claims.get("otp", Integer.class);

        PasswordResetOtp passwordResetOtp = findByUserAndOtp(user, otp);

        validateOtpExpiry(passwordResetOtp);
    }

    public void validateOtp(User user, Integer otp) {
        PasswordResetOtp passwordResetOtp = findByUserAndOtp(user, otp);

        validateOtpExpiry(passwordResetOtp);
    }

    public void resetPassword(String token, String password) {
        validatePasswordResetToken(token);

        Claims claims = jwtUtil.parseToken(token);
        Long userId = Long.valueOf(claims.getSubject());

        userService.updatePassword(userId, password);
    }
}
