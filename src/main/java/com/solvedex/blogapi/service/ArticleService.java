package com.solvedex.blogapi.service;

import com.solvedex.blogapi.db.entity.BlogArticle;
import com.solvedex.blogapi.exception.DataDoesntExistException;
import com.solvedex.blogapi.exception.NotOwnerException;
import com.solvedex.blogapi.exception.UserDoesntExistException;

import java.util.List;

public interface ArticleService {
    BlogArticle setArticle(String title, String content, Integer currentUserId) throws UserDoesntExistException;

    void deleteArticle(Integer articleId, Integer currentUserId) throws DataDoesntExistException, NotOwnerException;

    BlogArticle updateArticle(Integer articleId, String title, String content, Integer currentUserId) throws DataDoesntExistException, NotOwnerException;

    List<BlogArticle> getArticles();

}
