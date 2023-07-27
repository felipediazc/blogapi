package com.solvedex.blogapi.service;

import com.solvedex.blogapi.db.entity.BlogArticle;
import com.solvedex.blogapi.db.entity.BlogComment;
import com.solvedex.blogapi.db.entity.BlogUser;
import com.solvedex.blogapi.db.repository.BlogArticleRepository;
import com.solvedex.blogapi.db.repository.BlogCommentRepository;
import com.solvedex.blogapi.db.repository.BlogUserRepository;
import com.solvedex.blogapi.exception.DataDoesntExistException;
import com.solvedex.blogapi.exception.NotOwnerException;
import com.solvedex.blogapi.exception.UserDoesntExistException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final BlogCommentRepository blogCommentRepository;
    private final BlogUserRepository blogUserRepository;
    private final BlogArticleRepository blogArticleRepository;


    public CommentServiceImpl(BlogCommentRepository blogCommentRepository, BlogUserRepository blogUserRepository, BlogArticleRepository blogArticleRepository) {
        this.blogCommentRepository = blogCommentRepository;
        this.blogUserRepository = blogUserRepository;
        this.blogArticleRepository = blogArticleRepository;
    }

    @Override
    public BlogComment setComment(Integer articleId, String comment, Integer currentUserId) throws DataDoesntExistException, UserDoesntExistException {
        Optional<BlogUser> blogUserOptional = blogUserRepository.findById(currentUserId);
        if (blogUserOptional.isPresent()) {
            BlogUser blogUser = blogUserOptional.get();
            Optional<BlogArticle> blogArticleOptional = blogArticleRepository.findById(articleId);
            if (blogArticleOptional.isPresent()) {
                BlogArticle blogArticle = blogArticleOptional.get();
                BlogComment blogComment = new BlogComment();
                blogComment.setArticle(blogArticle);
                blogComment.setAuthor(blogUser);
                blogComment.setCommentdate(Instant.now());
                blogComment.setComments(comment);
                return blogCommentRepository.save(blogComment);
            }
            throw new DataDoesntExistException();
        }
        throw new UserDoesntExistException();
    }

    @Override
    public void deleteComment(Integer commentId, Integer currentUserId) throws DataDoesntExistException, NotOwnerException {
        Optional<BlogComment> blogCommentOptional = blogCommentRepository.findById(commentId);
        if (blogCommentOptional.isPresent()) {
            BlogComment blogComment = blogCommentOptional.get();
            if (blogComment.getAuthor().getId().equals(currentUserId)) {
                blogCommentRepository.delete(blogComment);
            } else {
                throw new NotOwnerException();
            }
        } else {
            throw new DataDoesntExistException();
        }
    }

    @Override
    public BlogComment updateComment(Integer commentId, String comment, Integer currentUserId) throws DataDoesntExistException, NotOwnerException {
        Optional<BlogComment> blogCommentOptional = blogCommentRepository.findById(commentId);
        if (blogCommentOptional.isPresent()) {
            BlogComment blogComment = blogCommentOptional.get();
            if (blogComment.getAuthor().getId().equals(currentUserId)) {
                blogComment.setComments(comment);
                blogComment.setCommentdate(Instant.now());
                return blogCommentRepository.save(blogComment);
            }
            throw new NotOwnerException();
        }
        throw new DataDoesntExistException();
    }

    @Override
    public List<BlogComment> getComments(Integer articleId) {
        return blogCommentRepository.findAll();
    }
}
