package com.springboot.post.service;

import com.springboot.post.entity.User;
import com.springboot.post.payload.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();
    UserDto getUserById(Long userId);
    List<UserDto> getFollowerList(Long userId);
    List<UserDto> getFollowingList(Long userId);
    User followUser(Long userId, Long targetUserId);
    void unfollowUser(Long userId, Long targetUserId);

}
