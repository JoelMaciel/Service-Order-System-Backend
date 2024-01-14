package com.joelmaciel.serviceorder.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joelmaciel.serviceorder.domain.enums.Priority;
import com.joelmaciel.serviceorder.domain.enums.Status;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderServiceDTO {

    private Integer id;
    private String observation;
    private Status status;
    private Integer technician;
    private Integer customer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private OffsetDateTime openingDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private OffsetDateTime closingDate;
    private Priority priority;
}

