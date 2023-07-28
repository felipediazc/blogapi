package com.solvedex.blogapi.controller;

import com.solvedex.blogapi.db.entity.BlogComment;
import com.solvedex.blogapi.dto.TokenDataDto;
import com.solvedex.blogapi.exception.DataDoesntExistException;
import com.solvedex.blogapi.exception.ExceptionResponse;
import com.solvedex.blogapi.exception.NotOwnerException;
import com.solvedex.blogapi.exception.UserDoesntExistException;
import com.solvedex.blogapi.service.AuthenticationToken;
import com.solvedex.blogapi.service.CommentService;
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
public class CommentController {

    private final AuthenticationToken authenticationToken;
    private final CommentService commentService;

    public CommentController(AuthenticationToken authenticationToken, CommentService commentService) {
        this.authenticationToken = authenticationToken;
        this.commentService = commentService;
    }

    @PostMapping(value = {"/comment"})
    @Operation(summary = "Creates a new comment entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully operation"),
            @ApiResponse(responseCode = "500", description = "User Doesn't exist", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Blog doesn't exist", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public BlogComment setComment(
            @RequestHeader(BlogUtil.TOKEN_HEADER) String token,
            @Parameter(description = "Blog Id") @RequestParam(name = "blogid") Integer blogId,
            @Parameter(description = "New comment") @RequestParam(name = "comment") String comment) throws DataDoesntExistException, UserDoesntExistException {
        TokenDataDto tokenDataDto = authenticationToken.getTokenData(token);
        return commentService.setComment(blogId, comment, tokenDataDto.getUserId());
    }

    @PutMapping(value = {"/comment"})
    @Operation(summary = "Updates a new blog comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully operation"),
            @ApiResponse(responseCode = "401", description = "Not owner error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Comment doesn't exist", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public BlogComment updateComment(
            @RequestHeader(BlogUtil.TOKEN_HEADER) String token,
            @Parameter(description = "Comment id") @RequestParam(name = "id") Integer id,
            @Parameter(description = "New comment") @RequestParam(name = "comment") String comment) throws DataDoesntExistException, NotOwnerException {
        TokenDataDto tokenDataDto = authenticationToken.getTokenData(token);
        return commentService.updateComment(id, comment, tokenDataDto.getUserId());
    }

    @DeleteMapping("/comment/{id}")
    @Operation(summary = "Deletes a comment entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully operation"),
            @ApiResponse(responseCode = "401", description = "Not owner error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Comment doesn't exist", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public void deleteComment(
            @RequestHeader(BlogUtil.TOKEN_HEADER) String token,
            @Parameter(description = "Comment Id") @PathVariable Integer id) throws DataDoesntExistException, NotOwnerException {

        TokenDataDto tokenDataDto = authenticationToken.getTokenData(token);
        commentService.deleteComment(id, tokenDataDto.getUserId());
    }

    @GetMapping("/comment/{id}")
    @Operation(summary = "Get all comments entries from a blog entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully operation")
    })
    public List<BlogComment> getArticles(@Parameter(description = "Blog Id") @PathVariable Integer id) {
        return commentService.getComments(id);
    }

}