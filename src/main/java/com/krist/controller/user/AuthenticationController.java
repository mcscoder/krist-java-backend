package com.krist.controller.user;

import javax.naming.AuthenticationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krist.dto.common.MessageDto;
import com.krist.dto.common.TokenDto;
import com.krist.dto.user.LoginDto;
import com.krist.dto.user.PasswordDto;
import com.krist.dto.user.RegisterDto;
import com.krist.service.authentication.AuthenticationService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    final private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageDto> register(@RequestBody RegisterDto registerDto) {
        authenticationService.register(registerDto);

        return ResponseEntity.ok(new MessageDto("Register success"));
    }

    @PostMapping("/login")
    public ResponseEntity<MessageDto> login(@RequestBody LoginDto loginDto,
            HttpServletResponse response) throws AuthenticationException {
        authenticationService.login(loginDto, response);

        return ResponseEntity.ok().body(new MessageDto("Log in success"));
    }

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<MessageDto> forgotPassword(@PathVariable String email) {
        authenticationService.forgotPassword(email);

        return ResponseEntity.ok().body(new MessageDto("OTP code has been sent to your email"));
    }

    @PostMapping("/otp/{email}/{otp}")
    public ResponseEntity<Object> otpAuthentication(@PathVariable String email,
            @PathVariable Integer otp) {
        TokenDto token = authenticationService.otpAuthentication(email, otp);

        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<Object> resetPassword(@PathVariable String token,
            @RequestBody PasswordDto passwordDto) {
        authenticationService.resetPassword(token, passwordDto.password());

        return ResponseEntity.ok().body(new MessageDto("Password has been changed"));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageDto> logout(HttpServletResponse response) {
        authenticationService.logout(response);

        return ResponseEntity.ok().body(new MessageDto("Logout success"));
    }
}
