package com.springboot.post.exception;

import org.springframework.http.HttpStatus;

public class PostAPIException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public PostAPIException(HttpStatus status, String message) {
        this.message = message;
        this.status = status;
    }

    public PostAPIException(HttpStatus status, String message, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
