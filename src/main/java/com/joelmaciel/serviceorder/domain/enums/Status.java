package com.joelmaciel.serviceorder.domain.enums;

import com.joelmaciel.serviceorder.domain.excptions.PriorityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    OPEN,
    IN_PROGRESS,
    CLOSED;
}
