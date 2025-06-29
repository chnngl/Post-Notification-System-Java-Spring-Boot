package com.springboot.post.controller;

import com.springboot.post.payload.PostDto;
import com.springboot.post.payload.UserDto;
import com.springboot.post.service.PostService;
import com.springboot.post.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    public UserController(UserService userService , PostService postService) {

        this.userService = userService;
        this.postService = postService;
    }

    //get all users
    @GetMapping("/users")
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    //get posts from user's following
    @GetMapping("/{userId}/feed")
    public ResponseEntity<List<PostDto>> getFollowingPosts(@PathVariable("userId") Long userId) {
        List<PostDto> posts = postService.getFollowingPosts(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //get following list from user
    @GetMapping("/{userId}/following")
    public ResponseEntity<List<UserDto>> getFollowingList(@PathVariable("userId") Long userId) {
        List<UserDto> following = userService.getFollowingList(userId);
        return new ResponseEntity<>(following, HttpStatus.OK);
    }

    //get follower list from user
    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<UserDto>> getFollowerList(@PathVariable("userId") Long userId) {
        List<UserDto> followers = userService.getFollowerList(userId);
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    //follow one user
    @PostMapping("/{userId}/follow/{targetUserId}")
    public ResponseEntity<Void> followUser(@PathVariable("userId") Long userId,
                                           @PathVariable("targetUserId") Long targetUserId) {
        userService.followUser(userId, targetUserId);
        String message = "Successfully followed user with ID: " + targetUserId;
        return new ResponseEntity(message, HttpStatus.OK);
    }

    //unfollow one user
    @DeleteMapping("/{userId}/unfollow/{targetUserId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable("userId") Long userId,
                                             @PathVariable("targetUserId") Long targetUserId) {
        userService.unfollowUser(userId, targetUserId);
        String message = "Successfully unfollowed user with ID: " + targetUserId;
        return new ResponseEntity(message, HttpStatus.OK);
    }

}
