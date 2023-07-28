package com.solvedex.blogapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvedex.blogapi.db.entity.BlogComment;
import com.solvedex.blogapi.dto.SignInResponseDto;
import com.solvedex.blogapi.init.TestSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CommentControllerTest extends TestSetup {

    private static MvcResult mvcResult;
    private static String token;
    private static Integer commentId;

    @Autowired
    Jackson2ObjectMapperBuilder mapperBuilder;

    @BeforeEach
    public void init() {
        setupDb();
    }

    @DisplayName("#1 Sign In and gets token")
    @Test
    void commentControllerTest1() throws Exception {
        mvcResult = this.mockMvc
                .perform(post("/signin")
                        .param("username", "test")
                        .param("password", "test1")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.name").value("test user"))
                .andReturn();
    }

    @DisplayName("#2 Creates a Comment")
    @Test
    void commentControllerTest2() throws Exception {
        assertNotNull(mvcResult);
        String jsonStr = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = mapperBuilder.build();
        SignInResponseDto signInResponseDto = objectMapper.readValue(jsonStr, SignInResponseDto.class);
        token = signInResponseDto.getJwtToken();
        mvcResult = this.mockMvc
                .perform(post("/comment")
                        .header("token", token)
                        .param("blogid", "1")
                        .param("comment", "this is a new comment")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.comments").value("this is a new comment"))
                .andReturn();
    }

    @DisplayName("#3 Updates a Comment entry")
    @Test
    void commentControllerTest3() throws Exception {
        assertNotNull(mvcResult);
        String jsonStr = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = mapperBuilder.build();
        BlogComment blogComment = objectMapper.readValue(jsonStr, BlogComment.class);
        commentId = blogComment.getId();
        this.mockMvc
                .perform(put("/comment")
                        .header("token", token)
                        .param("id", commentId.toString())
                        .param("comment", "and new content xxx")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.comments").value("and new content xxx"));
    }


    @DisplayName("#5 Gets all blog comments from blog post")
    @Test
    void commentControllerTest5() throws Exception {

        this.mockMvc
                .perform(get("/comment/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
