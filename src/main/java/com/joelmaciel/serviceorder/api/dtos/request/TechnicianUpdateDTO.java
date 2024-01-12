package com.joelmaciel.serviceorder.api.dtos.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class TechnicianUpdateDTO {

    @NotBlank
    private String phoneNumber;
}
