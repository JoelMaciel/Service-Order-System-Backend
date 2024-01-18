package com.joelmaciel.serviceorder.api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianDTO {

    private Integer id;
    private String name;
    private String cpf;
    private String jobFunction;
    private String phoneNumber;
}
