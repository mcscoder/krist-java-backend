package com.krist.service;

import java.util.Date;
import org.springframework.stereotype.Service;
import com.krist.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@Service
public class JwtService {
    final private JwtUtil jwtUtil;

    public JwtService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Generates an access token for the specified user ID.
     * 
     * @param userId the ID of the user for whom the access token is generated
     * @return the generated access token as a string
     */
    public String generateAccessToken(Long userId) {
        System.out.println();
        String jwt = jwtUtil.createToken(null, userId.toString());
        return jwt;
    }

    /**
     * Checks if the provided token is valid based on its expiration time.
     * 
     * @param token the token to be validated
     * @return true if the token is valid, false if it has expired
     * @throws JwtException if an issue occurs during token validation
     */
    public Boolean isTokenValid(String token) throws JwtException {
        Claims claims = jwtUtil.parseToken(token);

        if (claims.getExpiration().after(new Date())) {
            return false;
        }

        return true;
    }

    /**
     * Retrieves the user ID from the provided JWT token.
     *
     * @param token the JWT token from which the user ID is extracted
     * @return the user ID parsed from the token as a Long
     * @throws JwtException if an issue occurs during the parsing of the token
     */
    public Long getUserIdFromJwt(String token) throws JwtException {
        return Long.parseLong(jwtUtil.parseToken(token).getSubject());
    }
}
