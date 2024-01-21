package com.joelmaciel.serviceorder.api.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateDTO {

    @NotBlank
    private String name;
    @NotNull
    @CNPJ
    private String cnpj;
    @NotBlank
    private String phoneNumber;
}
