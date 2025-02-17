package com.patterns.order.service;

import com.patterns.common.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @Value("${kafka.topics.order-created}")
    private String orderCreatedTopic;

    public void publishOrderCreatedEvent(Long orderId, Long userId, String status, Double totalAmount) {
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setTimestamp(LocalDateTime.now());
        event.setSource("order-service");
        event.setOrderId(orderId);
        event.setUserId(userId);
        event.setOrderStatus(status);
        event.setTotalAmount(totalAmount);

        kafkaTemplate.send(orderCreatedTopic, event);
    }
}