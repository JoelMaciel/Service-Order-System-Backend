package com.joelmaciel.serviceorder.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("TECHNICIAN")
@AllArgsConstructor
public class Technician extends Person {

    @OneToMany(mappedBy = "technician")
    private List<OrderService> orderServiceList = new ArrayList<>();

    public Technician(Integer id, String name, String cpf, String phoneNumber) {
        super(id, name, cpf, phoneNumber);
    }

    public Technician() {
    }
}
