package com.solvedex.blogapi.services;

import com.solvedex.blogapi.db.entity.BlogUser;
import com.solvedex.blogapi.dto.SignInResponseDto;
import com.solvedex.blogapi.exception.AuthenticationException;
import com.solvedex.blogapi.exception.UserExistException;
import com.solvedex.blogapi.init.TestSetup;
import com.solvedex.blogapi.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class UserServiceTest extends TestSetup {

    @BeforeEach
    public void init() {
        setupDb();
    }

    @Autowired
    private UserService userService;

    @DisplayName("#1 SignUp a new user (happy path)")
    @SneakyThrows
    @Test
    void userServiceTest1() {
        BlogUser blogUser = userService.signUp("test2 user 2", "test2", "AAaa11@@");
        assertEquals("test2 user 2", blogUser.getName());
        assertEquals("test2", blogUser.getUsername());
        assertNotNull(blogUser.getPassword());
    }

    @DisplayName("#2 SignUp a new user (user exist exception)")
    @Test
    void userServiceTest2() {
        assertThrows(UserExistException.class, () -> {
            userService.signUp("test", "test", "AAaa11@@");
        });
    }

    @DisplayName("#3 SignIn (AuthenticationException user doesn't exist)")
    @Test
    void userServiceTest3() {
        assertThrows(AuthenticationException.class, () -> {
            userService.signIn("test3", "AAaa11@@");
        });
    }

    @DisplayName("#4 SignIn (Passwords do not match)")
    @Test
    void userServiceTest4() {
        assertThrows(AuthenticationException.class, () -> {
            userService.signIn("test2", "felipe");
        });
    }

    @DisplayName("#5 SignIn (Happy path)")
    @SneakyThrows
    @Test
    void userServiceTest5() {
        SignInResponseDto signInResponseDto = userService.signIn("test2", "AAaa11@@");
        assertNotNull(signInResponseDto.getJwtToken());
        assertEquals("test2", signInResponseDto.getUsername());
        assertEquals("test2 user 2", signInResponseDto.getName());
    }
}
