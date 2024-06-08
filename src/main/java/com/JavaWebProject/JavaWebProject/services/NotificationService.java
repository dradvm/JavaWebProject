/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Notification;
import com.JavaWebProject.JavaWebProject.repositories.NotificationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    
    public void save(Notification noti) {
        notificationRepository.save(noti);
    }
    public List<Notification> getNotification(String receiver) {
        return notificationRepository.findTop10ByReceiverOrderByNotificationTimeDesc(receiver);
    }
}
