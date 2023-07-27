package com.solvedex.blogapi.service;

public interface AuthenticationToken {
    String createToken(String username, Integer userId);

    Boolean isValidToken(String token);

    String getTokenSecret();

    Integer getTokenExpiration();
}
