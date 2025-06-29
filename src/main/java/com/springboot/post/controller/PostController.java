package com.springboot.post.controller;

import com.springboot.post.payload.PostDto;
import com.springboot.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{userId}")
public class PostController {

    @Autowired
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //get posts
    @GetMapping("/posts")
    public List<PostDto> getPostByUserId(@PathVariable(value="userId") Long userId){
        return postService.getPostByUserId(userId);
    }

    //create posts
    @PostMapping("/posts")
    public ResponseEntity<String> createPost(@PathVariable(value="userId") long userId,
                                             @RequestBody PostDto postDto) {
        try {
            postService.createPost(userId, postDto);
            return ResponseEntity.ok("Post created successfully.");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
