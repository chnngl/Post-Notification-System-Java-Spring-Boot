package com.springboot.post.config;

import com.springboot.post.entity.Notification;
import com.springboot.post.entity.Post;
import com.springboot.post.entity.User;
import com.springboot.post.payload.NotificationDto;
import com.springboot.post.payload.PostDto;
import com.springboot.post.payload.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // Configure custom mapping
        modelMapper.typeMap(Notification.class, NotificationDto.class).addMappings(mapper -> {
            mapper.map(Notification::getMessage, NotificationDto::setMessage);
            mapper.map(Notification::getCreatedAt, NotificationDto::setCreatedAt);
        });

        modelMapper.typeMap(Post.class, PostDto.class).addMappings(
                mapper -> {mapper.map(src -> src.getUser().getUsername(), PostDto::setUsername);
                   });
        modelMapper.typeMap(User.class, UserDto.class).addMappings(
                mapper -> {mapper.map(User::getId, UserDto::setId);
                mapper.map(User::getUsername, UserDto::setUsername);
        });
        return modelMapper;
}
}


