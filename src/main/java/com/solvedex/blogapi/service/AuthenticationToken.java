package com.solvedex.blogapi.service;

import com.solvedex.blogapi.dto.TokenDataDto;

public interface AuthenticationToken {
    String createToken(String username, Integer userId);

    Boolean isValidToken(String token);

    TokenDataDto getTokenData(String token);

    String getTokenSecret();

    Integer getTokenExpiration();
}
