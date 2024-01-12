package com.joelmaciel.serviceorder.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CUSTOMER")
@AllArgsConstructor
@Data
public class Customer extends Person {
    @OneToMany(mappedBy = "customer")
    private List<OrderService> orderServiceList = new ArrayList<>();


    public Customer(Integer id, String name, String cpf, String phoneNumber) {
        super(id, name, cpf, phoneNumber);
    }

    public Customer() {
    }
}
