package com.solvedex.blogapi.service;

import com.solvedex.blogapi.db.entity.BlogUser;
import com.solvedex.blogapi.dto.SignInResponseDto;
import com.solvedex.blogapi.exception.AuthenticationException;
import com.solvedex.blogapi.exception.UserExistException;

public interface UserService {
    BlogUser signUp(String name, String username, String password) throws UserExistException;

    SignInResponseDto signIn(String username, String password) throws AuthenticationException;

}
