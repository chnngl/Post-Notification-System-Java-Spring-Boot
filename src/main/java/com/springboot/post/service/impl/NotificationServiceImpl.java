package com.springboot.post.service.impl;

import com.springboot.post.entity.Notification;
import com.springboot.post.entity.User;
import com.springboot.post.payload.NotificationDto;
import com.springboot.post.repository.NotificationRepository;
import com.springboot.post.service.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;
    private ModelMapper mapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   ModelMapper mapper) {
        this.notificationRepository = notificationRepository;
        this.mapper = mapper;
    }

    @Override
    public List<NotificationDto> getNotificationByObserverId(long observerId) {
        List<Notification> notifications = notificationRepository.findByObserverIdOrderByCreatedAtDesc(observerId);
        return notifications.stream().map(notification->mapToDto(notification)).
                collect(Collectors.toList());
    }

    @Override
    public void saveAndNotify(String message, User user) {
        List<User> observers = user.getObservers().stream().collect(Collectors.toList());
        // save notifications to database
        if (observers != null && !observers.isEmpty()) {
            for (User observer : observers) {
                Notification notification = new Notification();
                notification.setMessage(message);
                notification.setObserver(observer);
                notificationRepository.save(notification);
         }
        }
    }

    //convert entity to DTO
    private NotificationDto mapToDto(Notification notification){
        NotificationDto notificationDto = mapper.map(notification, NotificationDto.class);
        return notificationDto;
    }
}


