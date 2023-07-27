package com.solvedex.blogapi.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "blog_users")
public class BlogUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "username", unique = true, nullable = false, length = 60)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "jwttoken", nullable = true, length = 600)
    private String jwttoken;

}