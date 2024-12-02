package com.digitalmarket.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    
    private final JwtUtil jwtUtil;  // Inject JwtUtil to validate the token

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Skip filtering for requests to /user/**
        if (request.getRequestURI().startsWith("/user")) {
            filterChain.doFilter(request, response);  // Continue with the filter chain without checking the token
            return;
        }

        // Extract token for all other requests
        String token = extractToken(request);
        logger.info("The token is == {}", token);
        
        // Validate the token
        if (token == null || !jwtUtil.validateToken(token, extractUsername(token))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
            return;
        }

        filterChain.doFilter(request, response);  // Continue the filter chain if the token is valid
    }

    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);  // Extract the token after "Bearer "
        }
        return null;
    }

    private String extractUsername(String token) {
        return jwtUtil.extractUsername(token);  // Extract the username from the token
    }
}
