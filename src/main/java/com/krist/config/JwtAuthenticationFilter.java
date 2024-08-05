package com.krist.config;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.naming.directory.InvalidAttributeValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.krist.entity.user.User;
import com.krist.repository.user.UserRepository;
import com.krist.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(UserRepository userRepository, JwtService jwtService) {
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
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request)
            throws InvalidAttributeValueException, NoSuchElementException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            throw new NoSuchElementException("Authorization header is missing");
        }

        if (!authHeader.startsWith("Bearer ")) {
            throw new InvalidAttributeValueException(
                    "Authorization header does not start with 'Bearer '");
        }

        return authHeader.substring(7);
    }
}
