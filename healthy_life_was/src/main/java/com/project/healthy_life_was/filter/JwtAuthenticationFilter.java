package com.project.healthy_life_was.filter;

import com.project.healthy_life_was.provider.JwtProvider;
import com.project.healthy_life_was.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            String token = (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
                    ? jwtProvider.removeBearer(authorizationHeader)
                    : null;
            if (token == null || !jwtProvider.isValidToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }
            Long userId = jwtProvider.getUsernameFromJwt(token);
            setAuthenticationContext(request, userId);

        } catch(Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}