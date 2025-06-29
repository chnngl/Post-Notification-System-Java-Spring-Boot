package com.springboot.post.service.impl;

import com.springboot.post.entity.Post;
import com.springboot.post.entity.TextPost;
import com.springboot.post.entity.User;
import com.springboot.post.exception.ResourceNotFoundException;
import com.springboot.post.payload.PostDto;
import com.springboot.post.repository.PostRepository;
import com.springboot.post.repository.UserRepository;
import com.springboot.post.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private NotificationServiceImpl notificationServiceImpl;
    private PostRepository postRepository;
    private UserRepository userRepository;
    private ModelMapper mapper;

    public PostServiceImpl(UserRepository userRepository, PostRepository postRepository,
                           ModelMapper mapper, NotificationServiceImpl notificationServiceImpl) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.notificationServiceImpl = notificationServiceImpl;
    }

    @Override
    public PostDto createPost(long userId, PostDto postDto) {

        //post characters must be less than 250 characters
        if (postDto.getPost().length() > 250) {
            throw new IllegalArgumentException("Post length exceeds the limit of 250 characters.");
        }
        // determine the post type;
        TextPost post = new TextPost();
        post.setPost(postDto.getPost());
        //retrieve user entity by id
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User","id",userId)
        );
        //set post to user entity
        post.setUser(user);
        //save post entity to DB
        Post newPost = postRepository.save(post);
        //notification message
        String message = "New post from " + user.getUsername() + ": " + postDto.getPost();
        // notify observers using the notifyObservers method
        user.notifyObservers(message);
        //save them to database
        notificationServiceImpl.saveAndNotify(message, user);
        return mapToDto(newPost);
    }

    @Override
    public List<PostDto> getPostByUserId(long userId) {
        // fetch posts by userId
        List <Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(userId);
        //convert list of posts to list of post dto
        return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    // fetch posts from users the current user is following
    public List<PostDto> getFollowingPosts(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userId));

        //fetch the following list from current user
        Set<User> following = user.getFollowing();
        //get following lists' posts and sort them based on created time
        List<Post> posts = following.stream().flatMap(followedUser ->
                        followedUser.getPosts().stream())
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());

        return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    //convert entity to DTO
    private PostDto mapToDto(Post post){
        PostDto postDto = mapper.map(post, PostDto.class);
        postDto.setUsername(post.getUser().getUsername());
        return postDto;
    }

    //convert DTO to entity
    private Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
        return post;
    }

}
