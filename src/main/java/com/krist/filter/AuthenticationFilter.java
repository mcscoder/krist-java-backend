package com.krist.filter;

import org.springframework.lang.NonNull;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public abstract class AuthenticationFilter extends OncePerRequestFilter {
    private RequestMatcher authenticationRequestMatcher;

    public AuthenticationFilter(RequestMatcher authenticationRequestMatcher) {
        this.authenticationRequestMatcher = authenticationRequestMatcher;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        return authenticationRequestMatcher.matches(request);
    }
}
