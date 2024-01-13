package com.joelmaciel.serviceorder.domain.excptions;

import java.util.UUID;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }
    public OrderNotFoundException(UUID orderId) {
        this(String.format("There is no order with this id %s saved in the database", orderId));
    }
}
