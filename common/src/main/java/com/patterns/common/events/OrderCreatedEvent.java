package com.patterns.common.events;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderCreatedEvent extends BaseEvent {
    private Long orderId;
    private Long userId;
    private String orderStatus;
    private Double totalAmount;
}