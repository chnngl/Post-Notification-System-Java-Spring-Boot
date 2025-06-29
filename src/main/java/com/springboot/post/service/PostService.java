package com.springboot.post.service;

import com.springboot.post.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(long userId, PostDto postDto);
    List<PostDto> getPostByUserId(long userId);
    List<PostDto> getFollowingPosts(Long userId);
}
