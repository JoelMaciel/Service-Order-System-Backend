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
public class CustomerUpdateDTO {

    @NotBlank
    private String phoneNumber;
}
