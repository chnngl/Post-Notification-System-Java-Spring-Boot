package com.springboot.post.service.impl;

import com.springboot.post.entity.User;
import com.springboot.post.exception.ResourceNotFoundException;
import com.springboot.post.payload.UserDto;
import com.springboot.post.repository.UserRepository;
import com.springboot.post.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public User followUser(Long userId, Long targetUserId) {
        //users couldn't follow themselves
        if (userId.equals(targetUserId)) {
            throw new IllegalArgumentException("Users cannot follow themselves.");
        }

        User user = userRepository.findById(userId) .orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userId));

        User targetUser = userRepository.findById(targetUserId) .orElseThrow(() ->
                new ResourceNotFoundException("User", "id", targetUserId));

        // check if the target user is already in the following list of the current user
        if (user.getFollowing().contains(targetUser)) {
            throw new IllegalArgumentException(user.getUsername() + " is already following " + targetUser.getUsername());
        }

        //add target user to the following list of current user
        user.getFollowing().add(targetUser);
        // add user to the observers list of the target user
        targetUser.addObserver(user);
        //save change to the database
        userRepository.save(user);
        userRepository.save(targetUser);
        return user;
    }

    @Override
    public void unfollowUser(Long userId, Long targetUserId) {
        //users couldn't unfollow themselves
        if (userId.equals(targetUserId)) {
            throw new IllegalArgumentException("Users cannot unfollow themselves.");
        }

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userId));

        User targetUser = userRepository.findById(targetUserId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", targetUserId));

        if (user.getFollowing().contains(targetUser)) {
            //remove target user from observer list
            user.getFollowing().remove(targetUser);
            targetUser.removeObserver(user);
            //save changes to the database
            userRepository.save(user);
            userRepository.save(targetUser);
        }
        //if target user is not in the following list
        else {
            throw new IllegalArgumentException(user.getUsername() + " is not following " + targetUser.getUsername());
        }
    }

    @Override
    public List<UserDto> getAllUsers(){
        List<User> users =userRepository.findAll();
        return users.stream().map(user -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow( () ->
                new ResourceNotFoundException("User", "id", userId) );
        return mapToUserDto(user);
    }

    @Override
    public List<UserDto> getFollowingList(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userId));

        Set<User> following = user.getFollowing();

        return following.stream() .map(followingList->mapToUserDto(followingList))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getFollowerList(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userId));

        Set<User> followers = user.getObservers();

        return followers.stream() .map(followerList->mapToUserDto(followerList))
                .collect(Collectors.toList());
    }

    //convert user entity to DTO
    private UserDto mapToUserDto(User user){
        UserDto userDto = mapper.map(user, UserDto.class);
        return userDto;
    }
}