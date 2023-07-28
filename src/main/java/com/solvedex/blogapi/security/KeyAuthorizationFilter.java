package com.solvedex.blogapi.security;

import com.solvedex.blogapi.dto.TokenDataDto;
import com.solvedex.blogapi.service.AuthenticationToken;
import com.solvedex.blogapi.util.BlogUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class KeyAuthorizationFilter extends OncePerRequestFilter {


    private final AuthenticationToken authenticationToken;

    public KeyAuthorizationFilter(AuthenticationToken authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        String token = BlogUtil.getTokenFromHeader(request);
        try {
            if (tokenExist(token)) {
                if (Boolean.TRUE.equals(authenticationToken.isValidToken(token))) {
                    TokenDataDto tokenDataDto = authenticationToken.getTokenData(token);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(tokenDataDto.getUsername(), null, getAuthorities(tokenDataDto));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new BadCredentialsException(BlogUtil.INVALID_TOKEN_RECEIVED);
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch (IOException | ServletException | BadCredentialsException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getOutputStream().print(BlogUtil.getInvalidTokenErrorString());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
    }

    private boolean tokenExist(String token) {
        return (token != null);
    }

    public Collection<SimpleGrantedAuthority> getAuthorities(TokenDataDto tokenDataDto) {
        return List.of(new SimpleGrantedAuthority(tokenDataDto.getRole()));
    }
}