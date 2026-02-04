package com.springboot.post.repository;

import com.springboot.post.entity.Post;
import com.springboot.post.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);
    Page<Post> findByUserInOrderByCreatedAtDesc(Collection<User> users, Pageable pageable);

}
