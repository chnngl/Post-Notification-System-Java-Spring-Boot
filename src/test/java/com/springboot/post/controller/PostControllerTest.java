package com.springboot.post.controller;

import com.springboot.post.payload.PostDto;
import com.springboot.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired MockMvc mvc;
    @MockBean PostService postService;

    @Test
    void getPostsByUser_returns200() throws Exception {
        Mockito.when(postService.getPostByUserId(4L)).thenReturn(List.of(new PostDto()));
        mvc.perform(get("/api/4/posts")).andExpect(status().isOk());
    }

    @Test
    void createPost_returns200_successMessage() throws Exception {
        mvc.perform(post("/api/4/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"post\":\"testing\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Post created successfully."));

        Mockito.verify(postService).createPost(eq(4L), any(PostDto.class));
    }

    @Test
    void createPost_whenServiceThrowsIllegalArgument_returns400() throws Exception {
        Mockito.doThrow(new IllegalArgumentException("Bad post"))
                .when(postService).createPost(eq(4L), any(PostDto.class));

        mvc.perform(post("/api/4/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"post\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Bad post"));
    }
}
