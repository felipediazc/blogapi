package com.solvedex.blogapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvedex.blogapi.db.entity.BlogArticle;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ArticleControllerTest extends TestSetup {

    private static MvcResult mvcResult;
    private static String token;
    private static Integer blogId;

    @Autowired
    Jackson2ObjectMapperBuilder mapperBuilder;

    @BeforeEach
    public void init() {
        setupDb();
    }

    @DisplayName("#1 Sign In and gets token")
    @Test
    void articleControllerTest1() throws Exception {
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

    @DisplayName("#2 Creates a Blog entry")
    @Test
    void articleControllerTest2() throws Exception {
        assertNotNull(mvcResult);
        String jsonStr = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = mapperBuilder.build();
        SignInResponseDto signInResponseDto = objectMapper.readValue(jsonStr, SignInResponseDto.class);
        token = signInResponseDto.getJwtToken();
        mvcResult = this.mockMvc
                .perform(post("/blog")
                        .header("token", token)
                        .param("title", "Title blog int test")
                        .param("content", "content integration test")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Title blog int test"))
                .andExpect(jsonPath("$.content").value("content integration test"))
                .andReturn();
    }

    @DisplayName("#3 Updates a Blog entry")
    @Test
    void articleControllerTest3() throws Exception {
        assertNotNull(mvcResult);
        String jsonStr = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = mapperBuilder.build();
        BlogArticle blogArticle = objectMapper.readValue(jsonStr, BlogArticle.class);
        blogId = blogArticle.getId();
        this.mockMvc
                .perform(put("/blog")
                        .header("token", token)
                        .param("id", blogId.toString())
                        .param("title", "this is new title")
                        .param("content", "and new content")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("this is new title"))
                .andExpect(jsonPath("$.content").value("and new content"));
    }

    @DisplayName("#4 Deletes blog")
    @Test
    void articleControllerTest4() throws Exception {

        this.mockMvc
                .perform(delete("/blog/" + blogId)
                        .header("token", token)
                )
                .andExpect(status().isOk());
    }

    @DisplayName("#5 Gets all blog entries")
    @Test
    void articleControllerTest5() throws Exception {

        this.mockMvc
                .perform(get("/blog"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
