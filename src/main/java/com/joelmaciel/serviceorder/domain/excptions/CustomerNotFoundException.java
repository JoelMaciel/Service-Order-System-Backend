package com.joelmaciel.serviceorder.domain.excptions;

public class CustomerNotFoundException extends ResourceNotFoundException {
    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(Integer customerId) {
        this(String.format("There is no such customer %d registered in the database ", customerId));
    }
}
