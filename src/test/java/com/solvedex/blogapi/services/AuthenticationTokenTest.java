package com.solvedex.blogapi.services;

import com.solvedex.blogapi.db.entity.BlogUser;
import com.solvedex.blogapi.db.repository.BlogUserRepository;
import com.solvedex.blogapi.dto.TokenDataDto;
import com.solvedex.blogapi.init.TestSetup;
import com.solvedex.blogapi.service.AuthenticationToken;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class AuthenticationTokenTest extends TestSetup {


    @Autowired
    AuthenticationToken authenticationToken;

    @Autowired
    BlogUserRepository blogUserRepository;

    private final String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzb2x2ZWRleCIsImp0aSI6IjEiLCJzdWIiOiJ0ZXN0IiwiYXV0aG9yaXRpZXMiOlsiR1VFU1QiXSwiaWF0IjoxNjkwNDI2NzI0LCJleHAiOjE2OTA0MjY3ODQsInVzZXJJZCI6MSwicm9sZSI6IkdVRVNUIn0.UX26J9x3jSea8Klwt4jS2NU0M1Q9BVTt0bp9Y5a6zCa9lw6bRzTfFBCXJSndR2L7Sw2E_UXnesTlL_IQH9UDWg";

    @BeforeEach
    public void init() {
        setupDb();
    }

    @DisplayName("#1 Test expired token")
    @SneakyThrows
    @Test
    void tokenTest1() {
        assertThrows(ExpiredJwtException.class, () -> authenticationToken.isValidToken(expiredToken));
    }

    @DisplayName("#2 Test null token")
    @SneakyThrows
    @Test
    void tokenTest2() {
        assertFalse(authenticationToken.isValidToken(null));
    }

    @DisplayName("#3 Test null username and null userId")
    @SneakyThrows
    @Test
    void tokenTest3() {
        String token = authenticationToken.createToken(null, null);
        assertFalse(authenticationToken.isValidToken(token));
    }

    @DisplayName("#4 Test no user for token")
    @SneakyThrows
    @Test
    void tokenTest4() {
        String token = authenticationToken.createToken("test5", 1);
        assertFalse(authenticationToken.isValidToken(token));
    }

    @DisplayName("#5 Test token happy path")
    @SneakyThrows
    @Test
    void tokenTest5() {
        String token = authenticationToken.createToken("test", 1);
        Optional<BlogUser> blogUserOptional = blogUserRepository.findByUsername("test");
        if (blogUserOptional.isPresent()) {
            BlogUser blogUser = blogUserOptional.get();
            blogUser.setJwttoken(token);
            blogUserRepository.save(blogUser);
        }
        assertTrue(authenticationToken.isValidToken(token));
        assertNull(authenticationToken.getTokenData(null));
        TokenDataDto tokenDataDto = authenticationToken.getTokenData(token);
        assertEquals(1, tokenDataDto.getUserId());
        assertEquals("test", tokenDataDto.getUsername());
        assertEquals("GUEST", tokenDataDto.getRole());
    }
}
