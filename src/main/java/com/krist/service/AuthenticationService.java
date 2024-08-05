package com.krist.service;

import javax.naming.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.krist.dto.common.TokenDto;
import com.krist.dto.user.LoginDto;
import com.krist.dto.user.RegisterDto;
import com.krist.entity.user.User;
import com.krist.exception.EmailAlreadyExistsException;
import com.krist.repository.user.UserRepository;

@Service
public class AuthenticationService {
    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;
    final private JwtService jwtService;


    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterDto registerDto) throws EmailAlreadyExistsException {
        if (userRepository.existsByEmail(registerDto.email())) {
            throw new EmailAlreadyExistsException("Email duplicated");
        }

        User newUser = new User();
        newUser.setFirstName(registerDto.firstName());
        newUser.setLastName(registerDto.lastName());
        newUser.setEmail(registerDto.email());
        newUser.setPassword(passwordEncoder.encode(registerDto.password()));

        userRepository.save(newUser);
    }

    public TokenDto login(LoginDto loginDto)
            throws UsernameNotFoundException, AuthenticationException {
        User user = userRepository.findByEmail(loginDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginDto.password(), user.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }

        String jwt = jwtService.generateAccessToken(user.getId());

        return new TokenDto(jwt);
    }
}
