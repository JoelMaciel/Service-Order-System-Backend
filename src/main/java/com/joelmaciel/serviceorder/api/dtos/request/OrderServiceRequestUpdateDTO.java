package com.joelmaciel.serviceorder.api.dtos.request;

import com.joelmaciel.serviceorder.domain.enums.Priority;
import com.joelmaciel.serviceorder.domain.enums.Status;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderServiceRequestUpdateDTO {

    @NotNull
    private Priority priority;
    @NotBlank
    private String observation;
    @NotNull
    private Status status;
    @NotNull
    private Integer technician;
}
