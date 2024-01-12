package com.joelmaciel.serviceorder.domain.enums;

import com.joelmaciel.serviceorder.domain.excptions.PriorityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Priority {
    LOW(0, "LOW"),
    MEDIUM(1, "MEDIUM"),
    HIGH(2, "HIGH");

    private final Integer code;
    private final String description;

    public static Priority toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (Priority priority : Priority.values()) {
            if (code.equals(priority.getCode())) {
                return priority;
            }
        }
        throw new PriorityNotFoundException(code);
    }
}
