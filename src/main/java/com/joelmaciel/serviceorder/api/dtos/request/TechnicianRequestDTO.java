package com.joelmaciel.serviceorder.api.dtos.request;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TechnicianRequestDTO {

    @NotBlank
    private String name;
    @CPF
    @NotNull
    private String cpf;
    @NotBlank
    private String phoneNumber;
}
