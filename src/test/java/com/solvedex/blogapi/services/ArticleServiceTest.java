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

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ArticleServiceTest extends TestSetup {

    @Autowired
    private ArticleService articleService;

    private static Integer articleId;

    @BeforeEach
    public void init() {
        setupDb();
    }

    @DisplayName("#1 Creates a new article (Happy path)")
    @SneakyThrows
    @Test
    void articleTest1() {
        BlogArticle blogArticle = articleService.setArticle("article 2", "this is my original content", 1);
        assertNotNull(blogArticle.getId());
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
        BlogArticle blogArticle = articleService.updateArticle(1, "article 1", "my new content", 1);
        assertNotNull(blogArticle.getId());
        assertEquals("article 1", blogArticle.getTitle());
        assertEquals("my new content", blogArticle.getContent());
    }

    @DisplayName("#4 Updates an article (throw not owner error)")
    @Test
    void articleTest4() {
        assertThrows(NotOwnerException.class, () -> {
            articleService.updateArticle(1, "article 1", "my new content", 20);
        });
    }

    @DisplayName("#5 Updates an article (throw data doesnt exist error)")
    @Test
    void articleTest5() {
        assertThrows(DataDoesntExistException.class, () -> {
            articleService.updateArticle(30, "article 1", "my new content", 1);
        });
    }

    @DisplayName("#6 Deletes an article (throw data doesnt exist error)")
    @Test
    void articleTest6() throws UserDoesntExistException {
        BlogArticle blogArticle = articleService.setArticle("article 2", "this is my original content 2", 1);
        articleId = blogArticle.getId();
        assertNotNull(articleId);
        assertThrows(DataDoesntExistException.class, () -> {
            articleService.deleteArticle(40, 1);
        });
    }

    @DisplayName("#7 Deletes an article (throw not owner)")
    @Test
    void articleTest7() {
        assertThrows(NotOwnerException.class, () -> {
            articleService.deleteArticle(articleId, 20);
        });
    }

    @DisplayName("#8 Deletes an article (happy path)")
    @SneakyThrows
    @Test
    void articleTest8() {
        assertDoesNotThrow(() -> {
            articleService.deleteArticle(articleId, 1);
        });
    }

    @DisplayName("#9 Gets Articles")
    @SneakyThrows
    @Test
    void articleTest9() {
        List<BlogArticle> blogArticleList = articleService.getArticles();
        assertTrue(blogArticleList.size() > 0);
    }
}
