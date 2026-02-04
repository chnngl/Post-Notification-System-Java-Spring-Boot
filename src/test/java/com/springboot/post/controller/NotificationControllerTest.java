package com.springboot.post.controller;

import com.springboot.post.payload.NotificationDto;
import com.springboot.post.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired MockMvc mvc;
    @MockBean NotificationService notificationService;

    @Test
    void getNotifications_returns200() throws Exception {
        Mockito.when(notificationService.getNotificationByObserverId(eq(1L)))
                .thenReturn(List.of(new NotificationDto()));

        mvc.perform(get("/api/1/notifications"))
                .andExpect(status().isOk());
    }
}

