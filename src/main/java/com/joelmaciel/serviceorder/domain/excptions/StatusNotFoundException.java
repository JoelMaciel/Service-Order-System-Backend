package com.joelmaciel.serviceorder.domain.excptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException(Integer code) {
        String.format("There is no status with this code %d", code);
    }
}
