package com.springboot.post.repository;

import com.springboot.post.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByObserverIdOrderByCreatedAtDesc(Long observerId);
}
