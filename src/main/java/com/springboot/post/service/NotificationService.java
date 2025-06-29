package com.springboot.post.service;

import com.springboot.post.entity.User;
import com.springboot.post.payload.NotificationDto;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> getNotificationByObserverId(long observerId);
    void saveAndNotify(String message, User user);
}
