package com.patterns.order.service;

import com.patterns.order.client.UserServiceClient;
import com.patterns.order.dto.UserValidationResponse;
import com.patterns.order.entity.Order;
import com.patterns.order.exception.InvalidUserException;
import com.patterns.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final UserServiceClient userServiceClient;
    private final OrderEventProducer eventProducer;

    public Order createOrder(Order order) {
        UserValidationResponse userValidation = userServiceClient.validateUser(order.getUserId());
        
        if (!userValidation.isActive()) {
            throw new InvalidUserException("User is not active");
        }
        
        Order savedOrder = orderRepository.save(order);
        
        // Publish order created event
        eventProducer.publishOrderCreatedEvent(
            savedOrder.getId(),
            savedOrder.getUserId(),
            savedOrder.getStatus(),
            savedOrder.getTotalAmount()
        );
        
        return savedOrder;
    }
}