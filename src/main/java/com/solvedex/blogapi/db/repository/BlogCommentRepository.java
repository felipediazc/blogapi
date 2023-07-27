package com.solvedex.blogapi.db.repository;

import com.solvedex.blogapi.db.entity.BlogComment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BlogCommentRepository extends CrudRepository<BlogComment, Integer> {
    List<BlogComment> findAll();
}