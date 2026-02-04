package com.springboot.post.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Post & Notification REST API")
                .version("1.0")
                .description("Spring Boot + MySQL API for users, follows, posts, feeds and notifications."));
    }
}
