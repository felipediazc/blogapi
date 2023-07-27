package com.solvedex.blogapi.service;

import com.solvedex.blogapi.db.entity.BlogArticle;
import com.solvedex.blogapi.db.entity.BlogUser;
import com.solvedex.blogapi.db.repository.BlogArticleRepository;
import com.solvedex.blogapi.db.repository.BlogUserRepository;
import com.solvedex.blogapi.exception.DataDoesntExistException;
import com.solvedex.blogapi.exception.NotOwnerException;
import com.solvedex.blogapi.exception.UserDoesntExistException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final BlogArticleRepository blogArticleRepository;
    private final BlogUserRepository blogUserRepository;

    public ArticleServiceImpl(BlogArticleRepository blogArticleRepository, BlogUserRepository blogUserRepository) {
        this.blogArticleRepository = blogArticleRepository;
        this.blogUserRepository = blogUserRepository;
    }

    @Override
    public BlogArticle setArticle(String title, String content, Integer currentUserId) throws UserDoesntExistException {
        Optional<BlogUser> blogUserOptional = blogUserRepository.findById(currentUserId);
        if (blogUserOptional.isPresent()) {
            BlogUser blogUser = blogUserOptional.get();
            BlogArticle blogArticle = new BlogArticle();
            blogArticle.setTitle(title);
            blogArticle.setContent(content);
            blogArticle.setAuthor(blogUser);
            blogArticle.setReleasedate(Instant.now());
            return blogArticleRepository.save(blogArticle);
        }
        throw new UserDoesntExistException();
    }

    @Override
    public void deleteArticle(Integer articleId, Integer currentUserId) throws DataDoesntExistException, NotOwnerException {
        Optional<BlogArticle> blogArticleOptional = blogArticleRepository.findById(articleId);
        if (blogArticleOptional.isPresent()) {
            BlogArticle blogArticle = blogArticleOptional.get();
            if (blogArticle.getAuthor().getId().equals(currentUserId)) {
                blogArticleRepository.delete(blogArticle);
            } else {
                throw new NotOwnerException();
            }
        } else {
            throw new DataDoesntExistException();
        }
    }

    @Override
    public BlogArticle updateArticle(Integer articleId, String title, String content, Integer currentUserId) throws DataDoesntExistException, NotOwnerException {
        Optional<BlogArticle> blogArticleOptional = blogArticleRepository.findById(articleId);
        if (blogArticleOptional.isPresent()) {
            BlogArticle blogArticle = blogArticleOptional.get();
            if (blogArticle.getAuthor().getId().equals(currentUserId)) {
                blogArticle.setTitle(title);
                blogArticle.setContent(content);
                blogArticle.setReleasedate(Instant.now());
                return blogArticleRepository.save(blogArticle);
            }
            throw new NotOwnerException();
        }
        throw new DataDoesntExistException();
    }

    @Override
    public List<BlogArticle> getArticles() {
        return blogArticleRepository.findAll();
    }

}
