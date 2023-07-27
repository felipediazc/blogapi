package com.solvedex.blogapi.db.repository;

import com.solvedex.blogapi.db.entity.BlogArticle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BlogArticleRepository extends CrudRepository<BlogArticle, Integer> {

    List<BlogArticle> findAll();
}