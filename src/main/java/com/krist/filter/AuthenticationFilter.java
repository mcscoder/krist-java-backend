package com.krist.filter;

import org.springframework.lang.NonNull;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public abstract class AuthenticationFilter extends OncePerRequestFilter {

    private final RequestMatcher authenticationRequestMatcher;
    private final RequestMatcher publicRequestMatcher;

    public AuthenticationFilter(RequestMatcher authenticationRequestMatcher,
            RequestMatcher publicRequestMatcher) {
        this.authenticationRequestMatcher = authenticationRequestMatcher;
        this.publicRequestMatcher = publicRequestMatcher;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        return authenticationRequestMatcher.matches(request)
                || publicRequestMatcher.matches(request);
    }
}
