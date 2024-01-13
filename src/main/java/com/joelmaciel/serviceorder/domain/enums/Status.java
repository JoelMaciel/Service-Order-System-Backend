package com.joelmaciel.serviceorder.domain.enums;

import com.joelmaciel.serviceorder.domain.excptions.PriorityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    OPEN(0, "OPEN"),
    IN_PROGRESS(1, " IN_PROGRESS"),
    CLOSED(2, "CLOSED");

    private final Integer code;
    private final String description;

    public static Status toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (Status status : Status.values()) {
            if (code.equals(status.getCode())) {
                return status;
            }
        }
        throw new PriorityNotFoundException(code);
    }
}
