package com.springboot.post.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private long id;
    private String post;
    private LocalDateTime createdAt;
    private String username;
}
