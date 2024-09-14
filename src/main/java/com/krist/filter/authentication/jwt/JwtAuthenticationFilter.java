package com.krist.filter.authentication.jwt;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.directory.InvalidAttributeValueException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import com.krist.entity.user.User;
import com.krist.filter.authentication.AuthenticationFilter;
import com.krist.repository.user.UserRepository;
import com.krist.service.authentication.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends AuthenticationFilter {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(RequestMatcher authenticationRequestMatcher,
            RequestMatcher publicRequestMatcher, UserRepository userRepository,
            JwtService jwtService) {
        super(authenticationRequestMatcher, publicRequestMatcher);
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("Filter works");

        try {
            String jwt = getJwtFromRequest(request);

            Long userId = jwtService.getUserIdFromJwt(jwt);

            User user = userRepository.findById(userId).get();

            UsernamePasswordAuthenticationToken authReq =
                    new UsernamePasswordAuthenticationToken(user, null, List.of());

            SecurityContext context = SecurityContextHolder.createEmptyContext();

            context.setAuthentication(authReq);
            SecurityContextHolder.setContext(context);
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request)
            throws InvalidAttributeValueException, NoSuchElementException {
        if (request.getCookies() == null) {
            throw new NoSuchElementException("No cookies found in the request");
        }

        for (Cookie cookie : request.getCookies()) {
            if ("jwt".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        throw new NoSuchElementException("JWT cookie is missing");
    }
}
