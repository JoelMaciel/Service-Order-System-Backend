package com.joelmaciel.serviceorder.domain.excptions;

public class ProductInsufficientQuantityException extends BusinessException {
    public ProductInsufficientQuantityException(String message) {
        super(message);
    }

    public ProductInsufficientQuantityException(Integer quantity) {
        this(String.format("Order quantity %d is greater than stock ", quantity));
    }
}
