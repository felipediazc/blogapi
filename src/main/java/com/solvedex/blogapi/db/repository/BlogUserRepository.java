package com.solvedex.blogapi.db.repository;

import com.solvedex.blogapi.db.entity.BlogUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BlogUserRepository extends CrudRepository<BlogUser, Integer> {
    Optional<BlogUser> findByUsername(String username);

}