package com.joelmaciel.serviceorder.api.dtos.request;

import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequestDTO {

    @NotBlank
    private String name;
    @CNPJ
    @NotNull
    private String cnpj;
    @NotBlank
    private String phoneNumber;
}
