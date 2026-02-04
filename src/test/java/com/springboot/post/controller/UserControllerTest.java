package com.springboot.post.controller;

import com.springboot.post.payload.UserDto;
import com.springboot.post.service.PostService;
import com.springboot.post.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired MockMvc mvc;

    @MockBean UserService userService;
    @MockBean PostService postService;

    @Test
    void getAllUsers_returns200_andJson() throws Exception {
        UserDto u1 = new UserDto();
        u1.setId(1L);
        u1.setUsername("Samson");

        UserDto u2 = new UserDto();
        u2.setId(2L);
        u2.setUsername("Chang");

        Mockito.when(userService.getAllUsers()).thenReturn(List.of(u1, u2));

        mvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("Samson"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("Chang"));
    }

    @Test
    void getUserById_returns200() throws Exception {
        UserDto u = new UserDto();
        u.setId(4L);
        u.setUsername("Keith");
        Mockito.when(userService.getUserById(4L)).thenReturn(u);

        mvc.perform(get("/api/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.username").value("Keith"));
    }

    @Test
    void followUser_callsService() throws Exception {
        mvc.perform(post("/api/1/follow/2"))
                .andExpect(status().isOk());

        Mockito.verify(userService).followUser(1L, 2L);
    }

    @Test
    void unfollowUser_callsService() throws Exception {
        mvc.perform(delete("/api/1/unfollow/2"))
                .andExpect(status().isOk());

        Mockito.verify(userService).unfollowUser(1L, 2L);
    }
}
