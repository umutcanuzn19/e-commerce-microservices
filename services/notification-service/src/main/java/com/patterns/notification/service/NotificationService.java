package com.patterns.notification.service;

import com.patterns.common.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    public void sendOrderNotification(OrderCreatedEvent event) {
        // Implementation for sending actual notifications (email, SMS, etc.)
        log.info("Sending notification for order: {}", event.getOrderId());
        log.info("Order amount: {}", event.getTotalAmount());
        log.info("User ID: {}", event.getUserId());
    }
}