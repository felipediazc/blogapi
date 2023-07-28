package com.solvedex.blogapi.controller;

import com.solvedex.blogapi.init.TestSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class UserControllerTest extends TestSetup {


    @BeforeEach
    public void init() {
        setupDb();
    }

    @DisplayName("#1 Sign In")
    @Test
    void userControllerTest1() throws Exception {
        this.mockMvc
                .perform(post("/signin")
                        .param("username", "test")
                        .param("password", "test1")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.name").value("test user"));
    }

    @DisplayName("#2 Sign Up")
    @Test
    void userControllerTest2() throws Exception {
        this.mockMvc
                .perform(post("/signup")
                        .param("name", "Felipe Diaz")
                        .param("username", "felipe")
                        .param("password", "felipe")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("felipe"))
                .andExpect(jsonPath("$.name").value("Felipe Diaz"));
    }
}
