package com.solvedex.blogapi.controller;

import com.solvedex.blogapi.db.entity.BlogArticle;
import com.solvedex.blogapi.dto.TokenDataDto;
import com.solvedex.blogapi.exception.DataDoesntExistException;
import com.solvedex.blogapi.exception.ExceptionResponse;
import com.solvedex.blogapi.exception.NotOwnerException;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final AuthenticationToken authenticationToken;

    public ArticleController(ArticleService articleService, AuthenticationToken authenticationToken) {
        this.articleService = articleService;
        this.authenticationToken = authenticationToken;
    }

    @PostMapping(value = {"/blog"})
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

    @PutMapping(value = {"/blog"})
    @Operation(summary = "Updates a blog entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully operation"),
            @ApiResponse(responseCode = "401", description = "Not owner error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Blog doesn't exist", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public BlogArticle updateArticle(
            @RequestHeader(BlogUtil.TOKEN_HEADER) String token,
            @Parameter(description = "Blog Id") @RequestParam(name = "id") Integer id,
            @Parameter(description = "Blog new title") @RequestParam(name = "title") String title,
            @Parameter(description = "Blog new content") @RequestParam(name = "content") String content) throws DataDoesntExistException, NotOwnerException {

        TokenDataDto tokenDataDto = authenticationToken.getTokenData(token);
        return articleService.updateArticle(id, title, content, tokenDataDto.getUserId());
    }

    @DeleteMapping("/blog/{id}")
    @Operation(summary = "Deletes a blog entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully operation"),
            @ApiResponse(responseCode = "401", description = "Not owner error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Blog doesn't exist", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public void deleteArticle(
            @RequestHeader(BlogUtil.TOKEN_HEADER) String token,
            @Parameter(description = "Blog Id") @PathVariable Integer id) throws DataDoesntExistException, NotOwnerException {

        TokenDataDto tokenDataDto = authenticationToken.getTokenData(token);
        articleService.deleteArticle(id, tokenDataDto.getUserId());
    }

    @GetMapping("/blog")
    @Operation(summary = "Get all blogs entries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully operation")
    })
    public List<BlogArticle> getArticles() {
        return articleService.getArticles();
    }
}
