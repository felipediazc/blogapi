package com.solvedex.blogapi.services;

import com.solvedex.blogapi.db.entity.BlogArticle;
import com.solvedex.blogapi.exception.DataDoesntExistException;
import com.solvedex.blogapi.exception.NotOwnerException;
import com.solvedex.blogapi.exception.UserDoesntExistException;
import com.solvedex.blogapi.init.TestSetup;
import com.solvedex.blogapi.service.ArticleService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class ArticleServiceTest extends TestSetup {

    @Autowired
    private ArticleService articleService;

    @BeforeEach
    public void init() {
        setupDb();
    }

    @DisplayName("#1 Creates a new article (Happy path)")
    @SneakyThrows
    @Test
    void articleTest1() {
        BlogArticle blogArticle = articleService.setArticle("article 2", "this is my original content", 1);
        assertEquals(2, blogArticle.getId());
    }

    @DisplayName("#2 Creates an article (throw user doesnt exist error)")
    @Test
    void articleTest2() {
        assertThrows(UserDoesntExistException.class, () -> {
            articleService.setArticle("article 1", "this is my original content", 10);
        });
    }

    @DisplayName("#3 Updates an article (Happy path)")
    @SneakyThrows
    @Test
    void articleTest3() {
        BlogArticle blogArticle = articleService.updateArticle(2, "article 1", "my new content", 1);
        assertEquals(2, blogArticle.getId());
        assertEquals("article 1", blogArticle.getTitle());
        assertEquals("my new content", blogArticle.getContent());
    }

    @DisplayName("#4 Updates an article (throw not owner error)")
    @Test
    void articleTest4() {
        assertThrows(NotOwnerException.class, () -> {
            articleService.updateArticle(2, "article 1", "my new content", 2);
        });
    }

    @DisplayName("#5 Updates an article (throw data doesnt exist error)")
    @Test
    void articleTest5() {
        assertThrows(DataDoesntExistException.class, () -> {
            articleService.updateArticle(3, "article 1", "my new content", 1);
        });
    }

    @DisplayName("#6 Deletes an article (throw data doesnt exist error)")
    @Test
    void articleTest6() throws UserDoesntExistException {
        BlogArticle blogArticle = articleService.setArticle("article 2", "this is my original content 2", 1);
        assertEquals(3, blogArticle.getId());
        assertThrows(DataDoesntExistException.class, () -> {
            articleService.deleteArticle(4, 1);
        });
    }

    @DisplayName("#7 Deletes an article (throw not owner)")
    @Test
    void articleTest7() {
        assertThrows(NotOwnerException.class, () -> {
            articleService.deleteArticle(3, 2);
        });
    }

    @DisplayName("#8 Deletes an article (happy path)")
    @SneakyThrows
    @Test
    void articleTest8() {
        articleService.deleteArticle(3, 1);
    }

    @DisplayName("#9 Gets Articles")
    @SneakyThrows
    @Test
    void articleTest9() {
        List<BlogArticle> blogArticleList = articleService.getArticles();
        assertEquals(2, blogArticleList.size());
    }
}
