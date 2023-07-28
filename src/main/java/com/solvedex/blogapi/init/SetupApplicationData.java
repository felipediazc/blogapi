package com.solvedex.blogapi.init;

import com.solvedex.blogapi.exception.DataDoesntExistException;
import com.solvedex.blogapi.exception.UserDoesntExistException;
import com.solvedex.blogapi.exception.UserExistException;
import com.solvedex.blogapi.service.ArticleService;
import com.solvedex.blogapi.service.CommentService;
import com.solvedex.blogapi.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("!test")
public class SetupApplicationData {

    private final UserService userService;
    private final ArticleService articleService;

    private final CommentService commentService;

    public SetupApplicationData(UserService userService, ArticleService articleService, CommentService commentService) {
        this.userService = userService;
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @PostConstruct
    public void init() throws UserExistException, UserDoesntExistException, DataDoesntExistException {
        log.info("************************** SETTING INITIAL DATA (NO TEST) ***********************");
        userService.signUp("User for testing 1", "test1", "test1");
        userService.signUp("User for testing 2", "test2", "test2");
        userService.signUp("User for testing 3", "test3", "test3");
        articleService.setArticle("article title 1", "article content 1", 1);
        articleService.setArticle("article title 2", "article content 2", 2);
        articleService.setArticle("article title 2", "article content 3", 3);
        commentService.setComment(1, "comment 1 for article 1", 1);
        commentService.setComment(1, "comment 2 for article 1", 1);
        commentService.setComment(1, "comment 3 for article 1", 1);
        commentService.setComment(2, "comment 1 for article 2", 1);
        commentService.setComment(2, "comment 2 for article 2", 1);
        commentService.setComment(2, "comment 3 for article 2", 1);
        commentService.setComment(3, "comment 1 for article 3", 3);
        commentService.setComment(3, "comment 2 for article 3", 2);
        commentService.setComment(3, "comment 3 for article 3", 1);
    }
}
