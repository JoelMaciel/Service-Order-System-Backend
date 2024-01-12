package com.joelmaciel.serviceorder.domain.excptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PriorityNotFoundException extends RuntimeException {
    public PriorityNotFoundException(Integer code) {
        String.format("There is no priority with this code %d", code);
    }
}
