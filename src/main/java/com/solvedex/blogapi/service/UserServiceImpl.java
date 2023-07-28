package com.solvedex.blogapi.service;

import com.solvedex.blogapi.db.entity.BlogUser;
import com.solvedex.blogapi.db.repository.BlogUserRepository;
import com.solvedex.blogapi.dto.SignInResponseDto;
import com.solvedex.blogapi.exception.AuthenticationException;
import com.solvedex.blogapi.exception.UserExistException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final BlogUserRepository blogUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationToken authenticationToken;

    public UserServiceImpl(BlogUserRepository blogUserRepository, PasswordEncoder passwordEncoder, AuthenticationToken authenticationToken) {
        this.blogUserRepository = blogUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationToken = authenticationToken;
    }

    @Override
    public BlogUser signUp(String name, String username, String password) throws UserExistException {
        Optional<BlogUser> blogUserOptional = blogUserRepository.findByUsername(username);
        if (blogUserOptional.isPresent()) {
            throw new UserExistException();
        }
        BlogUser blogUser = new BlogUser();
        blogUser.setName(name);
        blogUser.setUsername(username);
        password = passwordEncoder.encode(password);
        blogUser.setPassword(password);
        return blogUserRepository.save(blogUser);
    }

    @Override
    public SignInResponseDto signIn(String username, String password) throws AuthenticationException {
        Optional<BlogUser> blogUserOptional = blogUserRepository.findByUsername(username);
        if (blogUserOptional.isPresent()) {
            BlogUser blogUser = blogUserOptional.get();
            if (passwordEncoder.matches(password, blogUser.getPassword())) {
                SignInResponseDto signInResponseDto = new SignInResponseDto();
                signInResponseDto.setName(blogUser.getName());
                signInResponseDto.setUsername(username);
                String token = authenticationToken.createToken(username, blogUser.getId());
                signInResponseDto.setJwtToken(token);
                blogUser.setJwttoken(token);
                blogUserRepository.save(blogUser);
                return signInResponseDto;
            }
        }
        throw new AuthenticationException();
    }
}
