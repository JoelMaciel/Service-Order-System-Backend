package com.joelmaciel.serviceorder.domain.services;

import com.joelmaciel.serviceorder.api.dtos.request.CustomerRequestDTO;
import com.joelmaciel.serviceorder.api.dtos.request.CustomerUpdateDTO;
import com.joelmaciel.serviceorder.api.dtos.response.CustomerDTO;
import com.joelmaciel.serviceorder.domain.entities.Customer;
import com.joelmaciel.serviceorder.domain.entities.Technician;

import java.util.List;

public interface CustomerService {
    CustomerDTO findById(Integer customerId);

    Customer findByCustomerId(Integer customerId);

    List<CustomerDTO> findAll();

    CustomerDTO update(Integer customerId, CustomerUpdateDTO customerUpdateDTO);

    CustomerDTO save(CustomerRequestDTO customerRequestDTO);

    void delete(Integer customerId);
}
