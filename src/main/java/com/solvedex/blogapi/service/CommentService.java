package com.solvedex.blogapi.service;

import com.solvedex.blogapi.db.entity.BlogComment;
import com.solvedex.blogapi.exception.DataDoesntExistException;
import com.solvedex.blogapi.exception.NotOwnerException;
import com.solvedex.blogapi.exception.UserDoesntExistException;

import java.util.List;

public interface CommentService {
    BlogComment setComment(Integer articleId, String comment, Integer currentUserId) throws DataDoesntExistException, UserDoesntExistException;

    void deleteComment(Integer commentId, Integer currentUserId) throws DataDoesntExistException, NotOwnerException;

    BlogComment updateComment(Integer commentId, String comment, Integer currentUserId) throws DataDoesntExistException, NotOwnerException;

    List<BlogComment> getComments(Integer articleId);
}
