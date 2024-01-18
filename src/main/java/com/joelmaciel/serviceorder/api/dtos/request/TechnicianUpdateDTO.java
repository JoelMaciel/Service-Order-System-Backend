package com.joelmaciel.serviceorder.api.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianUpdateDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String jobFunction;
    @NotBlank
    private String phoneNumber;
}
