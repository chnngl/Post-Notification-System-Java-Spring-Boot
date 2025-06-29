package com.springboot.post.controller;

import com.springboot.post.payload.NotificationDto;
import com.springboot.post.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{observerId}/notifications")
    public List<NotificationDto> getNotifications(@PathVariable Long observerId) {
        return notificationService.getNotificationByObserverId(observerId);
    }
}

