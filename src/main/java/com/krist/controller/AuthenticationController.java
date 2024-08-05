package com.krist.controller;

import javax.naming.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.krist.dto.common.MessageDto;
import com.krist.dto.common.TokenDto;
import com.krist.dto.user.LoginDto;
import com.krist.dto.user.RegisterDto;
import com.krist.enums.HttpStatusCode;
import com.krist.exception.EmailAlreadyExistsException;
import com.krist.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<MessageDto> register(@RequestBody RegisterDto registerDto) {
        logger.info("Register request received for email: {}", registerDto.email());
        try {
            authenticationService.register(registerDto);
            logger.info("User registered successfully with email: {}", registerDto.email());
            return ResponseEntity.ok(new MessageDto("Register success"));
        } catch (EmailAlreadyExistsException e) {
            logger.error("Email already exists: {}", registerDto.email(), e);
            return ResponseEntity.status(HttpStatusCode.CONFLICT.getCode())
                    .body(new MessageDto(e.getMessage()));
        } catch (Exception e) {
            logger.error("An unexpected error occurred during registration", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        try {
            TokenDto tokenDto = authenticationService.login(loginDto);
            logger.info("User login successfully with email: {}", loginDto.email());
            return ResponseEntity.ok().body(tokenDto);
        } catch (UsernameNotFoundException e) {
            logger.error("Username not found for email: {}", loginDto.email(), e);
            return ResponseEntity.status(HttpStatusCode.NOT_FOUND.getCode())
                    .body(new MessageDto(e.getMessage()));
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for email: {}", loginDto.email(), e);
            return ResponseEntity.status(HttpStatusCode.BAD_REQUEST.getCode())
                    .body(new MessageDto(e.getMessage()));
        } catch (Exception e) {
            logger.error("An unexpected error occurred during login for email: {}",
                    loginDto.email(), e);
            return ResponseEntity.status(HttpStatusCode.INTERNAL_SERVER_ERROR.getCode())
                    .body(new MessageDto("An unexpected error occurred"));
        }
    }
}
