package com.project.healthy_life_was.healthy_life.filter;

import com.project.healthy_life_was.healthy_life.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

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

            String username = jwtProvider.getUsernameFromJwt(token);
            String nickName = jwtProvider.getNickNameFromJwt(token);

            setAuthenticationContext(request, username, nickName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationContext(HttpServletRequest request, String username, String nickName) {
        AbstractAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.NO_AUTHORITIES);

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(securityContext);
    }
}

