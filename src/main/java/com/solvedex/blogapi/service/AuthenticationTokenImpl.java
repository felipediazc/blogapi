package com.solvedex.blogapi.service;

import com.solvedex.blogapi.db.entity.BlogUser;
import com.solvedex.blogapi.db.repository.BlogUserRepository;
import com.solvedex.blogapi.dto.TokenDataDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class AuthenticationTokenImpl implements AuthenticationToken {


    public static final String USER_ID = "userId";
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.expiration_in_seconds}")
    private Integer JWT_EXPIRATION;

    private static final String DEFAULT_USER_ROLE = "GUEST";
    private final BlogUserRepository blogUserRepository;

    public AuthenticationTokenImpl(BlogUserRepository blogUserRepository) {
        this.blogUserRepository = blogUserRepository;
    }

    @Override
    public String createToken(String username, Integer userId) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(DEFAULT_USER_ROLE);

        return Jwts
                .builder()
                .setIssuer("solvedex")
                .setId("solvedexJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (getTokenExpiration() * 1000)))
                .claim(USER_ID, userId)
                .claim("role", DEFAULT_USER_ROLE)
                .signWith(SignatureAlgorithm.HS512,
                        getTokenSecret()).compact();
    }

    @Override
    public Boolean isValidToken(String token) {
        if (token != null) {
            Claims claims = Jwts.parser().setSigningKey(getTokenSecret())
                    .parseClaimsJws(token)
                    .getBody();
            String username = claims.getSubject();
            String userId = null;
            if (claims.get(USER_ID) != null) {
                userId = claims.get(USER_ID).toString();
            }
            String role = (String) claims.get("role");
            log.info("Token data is: username -> {},  userId -> {} roles -> {}", username, userId, role);
            if (username != null && userId != null) {
                Optional<BlogUser> blogUserOptional = blogUserRepository.findByUsername(username);
                if (blogUserOptional.isPresent()) {
                    BlogUser blogUser = blogUserOptional.get();
                    return blogUser.getJwttoken().equals(token);
                }
            }
        }
        return false;
    }

    @Override
    public TokenDataDto getTokenData(String token) {
        if (token != null) {
            Claims claims = Jwts.parser().setSigningKey(getTokenSecret())
                    .parseClaimsJws(token)
                    .getBody();
            String username = claims.getSubject();
            String userId = null;
            if (claims.get(USER_ID) != null) {
                userId = claims.get(USER_ID).toString();
            }
            String role = (String) claims.get("role");
            if (username != null && userId != null) {
                TokenDataDto tokenDataDto = new TokenDataDto();
                tokenDataDto.setRole(role);
                tokenDataDto.setUsername(username);
                tokenDataDto.setUserId(Integer.parseInt(userId));
                return tokenDataDto;
            }
        }
        return null;
    }

    @Override
    public String getTokenSecret() {
        return JWT_SECRET;
    }

    @Override
    public Integer getTokenExpiration() {
        return JWT_EXPIRATION;
    }
}

