package com.solvedex.blogapi.services;

import com.solvedex.blogapi.db.entity.BlogComment;
import com.solvedex.blogapi.exception.DataDoesntExistException;
import com.solvedex.blogapi.exception.NotOwnerException;
import com.solvedex.blogapi.exception.UserDoesntExistException;
import com.solvedex.blogapi.init.TestSetup;
import com.solvedex.blogapi.service.CommentService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CommentServiceTest extends TestSetup {

    @Autowired
    private CommentService commentService;

    @BeforeEach
    public void init() {
        setupDb();
    }

    @DisplayName("#1 Creates a new comment (Happy path)")
    @SneakyThrows
    @Test
    void commentTest1() {
        BlogComment blogComment = commentService.setComment(1, "comments kjkj k", 1);
        assertNotNull(blogComment.getId());
    }

    @DisplayName("#2 Creates a new comment (throw user doesnt exist error)")
    @Test
    void commentTest2() {
        assertThrows(UserDoesntExistException.class, () -> {
            commentService.setComment(1, "comments kjkj k", 10);
        });
    }

    @DisplayName("#3 Creates a new comment (throw data doesnt exist)")
    @Test
    void commentTest3() {
        assertThrows(DataDoesntExistException.class, () -> {
            commentService.setComment(40, "comments kjkj k", 1);
        });
    }

    @DisplayName("#4 Updates a comment (Happy path)")
    @SneakyThrows
    @Test
    void commentTest4() {
        BlogComment blogComment = commentService.updateComment(1, "My updated comment", 1);
        assertEquals(1, blogComment.getId());
        assertEquals("My updated comment", blogComment.getComments());
    }

    @DisplayName("#5 Updates a comment (throw not owner error)")
    @Test
    void commentTest5() {
        assertThrows(NotOwnerException.class, () -> {
            commentService.updateComment(1, "My updated comment", 20);
        });
    }

    @DisplayName("#6 Updates a comment (throw data doesnt exist)")
    @Test
    void commentTest6() {
        assertThrows(DataDoesntExistException.class, () -> {
            commentService.updateComment(100, "My updated comment", 1);
        });
    }

    @DisplayName("#7 Deletes a comment (throw data doesnt exist)")
    @Test
    void commentTest7() {
        assertThrows(DataDoesntExistException.class, () -> {
            commentService.deleteComment(40, 1);
        });
    }

    @DisplayName("#8 Deletes a comment (throw not owner error)")
    @Test
    void commentTest8() {
        assertThrows(NotOwnerException.class, () -> {
            commentService.deleteComment(1, 20);
        });
    }

    @DisplayName("#9 Deletes a comment (happy path)")
    @SneakyThrows
    @Test
    void commentTest9() {
        BlogComment blogComment = commentService.setComment(1, "an other comment", 1);
        assertDoesNotThrow(() -> {
            commentService.deleteComment(blogComment.getId(), 1);
        });
    }

    @DisplayName("#10 Gets Comments")
    @SneakyThrows
    @Test
    void articleTest010() {
        List<BlogComment> blogCommentList = commentService.getComments(1);
        assertTrue(blogCommentList.size() > 0);
    }

}
