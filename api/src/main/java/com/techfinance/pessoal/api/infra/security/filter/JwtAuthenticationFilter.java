package com.techfinance.pessoal.api.infra.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.techfinance.pessoal.api.infra.security.jwt.JwtService;
import com.techfinance.pessoal.api.user.application.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final static String AUTHENTICATION_HEADER = "Authorization";
    private final static String PREFIX_AUTHENTICATION_HEADER = "Bearer ";

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHENTICATION_HEADER);

        if (authHeader == null || !authHeader.startsWith(PREFIX_AUTHENTICATION_HEADER)) {
            filterChain.doFilter(request, response);
            return ;
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        UserDetails user = userService.loadUserByUsername(username);

        var auth = new UsernamePasswordAuthenticationToken(
            user,
            null,
            user.getAuthorities()
        );

        SecurityContextHolder.getContext()
            .setAuthentication(auth);

        filterChain.doFilter(request, response);

    }

}
