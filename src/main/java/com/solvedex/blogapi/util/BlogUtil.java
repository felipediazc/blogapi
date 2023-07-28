package com.solvedex.blogapi.util;

import jakarta.servlet.http.HttpServletRequest;

public class BlogUtil {

    public static final String INVALID_TOKEN_RECEIVED = "Invalid Token received!";
    public static final String TOKEN_HEADER = "token";

    private BlogUtil() {
        throw new IllegalStateException("Utility class");
    }
    public static String getInvalidTokenErrorString() {
        return new StringBuilder("{ \"error\": \"")
                .append(INVALID_TOKEN_RECEIVED).append("\" }").toString();
    }

    public static String getTokenFromHeader(HttpServletRequest request) {
        return request.getHeader(TOKEN_HEADER);
    }
}
