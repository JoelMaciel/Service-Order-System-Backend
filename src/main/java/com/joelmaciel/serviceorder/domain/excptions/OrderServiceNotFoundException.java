package com.joelmaciel.serviceorder.domain.excptions;

public class OrderServiceNotFoundException extends ResourceNotFoundException {
    public OrderServiceNotFoundException(String message) {
        super(message);
    }
}
