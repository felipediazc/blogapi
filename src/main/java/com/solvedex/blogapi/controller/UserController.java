package com.solvedex.blogapi.controller;

import com.solvedex.blogapi.dto.SignInResponseDto;
import com.solvedex.blogapi.exception.AuthenticationException;
import com.solvedex.blogapi.exception.ExceptionResponse;
import com.solvedex.blogapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = {"/signin"})
    @Operation(summary = "Authenticates an user and gets authentication token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully operation"),
            @ApiResponse(responseCode = "401", description = "Authentication error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public SignInResponseDto signIn(@Parameter(description = "username") @RequestParam(name = "username") String username,
                                    @Parameter(description = "user's password") @RequestParam(name = "password") String password) throws AuthenticationException {

        return userService.signIn(username, password);
    }

}
