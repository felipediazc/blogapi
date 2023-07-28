package com.solvedex.blogapi.controller;

import com.solvedex.blogapi.db.entity.BlogArticle;
import com.solvedex.blogapi.dto.TokenDataDto;
import com.solvedex.blogapi.exception.ExceptionResponse;
import com.solvedex.blogapi.exception.UserDoesntExistException;
import com.solvedex.blogapi.service.ArticleService;
import com.solvedex.blogapi.service.AuthenticationToken;
import com.solvedex.blogapi.util.BlogUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final AuthenticationToken authenticationToken;

    public ArticleController(ArticleService articleService, AuthenticationToken authenticationToken) {
        this.articleService = articleService;
        this.authenticationToken = authenticationToken;
    }

    @PostMapping(value = {"/setarticle"})
    @Operation(summary = "Creates a new blog entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully operation"),
            @ApiResponse(responseCode = "500", description = "Authentication error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public BlogArticle setArticle(
            @RequestHeader(BlogUtil.TOKEN_HEADER) String token,
            @Parameter(description = "Blog title") @RequestParam(name = "title") String title,
            @Parameter(description = "Blog content") @RequestParam(name = "content") String content) throws UserDoesntExistException {
        TokenDataDto tokenDataDto = authenticationToken.getTokenData(token);
        return articleService.setArticle(title, content, tokenDataDto.getUserId());
    }
}
