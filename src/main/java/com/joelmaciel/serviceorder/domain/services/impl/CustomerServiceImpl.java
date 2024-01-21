package com.joelmaciel.serviceorder.domain.services.impl;

import com.joelmaciel.serviceorder.api.dtos.request.CustomerRequestDTO;
import com.joelmaciel.serviceorder.api.dtos.request.CustomerUpdateDTO;
import com.joelmaciel.serviceorder.api.dtos.request.TechnicianRequestDTO;
import com.joelmaciel.serviceorder.api.dtos.request.TechnicianUpdateDTO;
import com.joelmaciel.serviceorder.api.dtos.response.CustomerDTO;
import com.joelmaciel.serviceorder.api.dtos.response.TechnicianDTO;
import com.joelmaciel.serviceorder.domain.entities.Customer;
import com.joelmaciel.serviceorder.domain.entities.Technician;
import com.joelmaciel.serviceorder.domain.excptions.CustomerNotFoundException;
import com.joelmaciel.serviceorder.domain.excptions.TechnicianNotFoundException;
import com.joelmaciel.serviceorder.domain.repositories.CustomerRepository;
import com.joelmaciel.serviceorder.domain.repositories.TechnicianRepository;
import com.joelmaciel.serviceorder.domain.services.CustomerService;
import com.joelmaciel.serviceorder.domain.services.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> findAll() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CustomerDTO update(Integer customerId, CustomerUpdateDTO customerUpdateDTO) {
        Customer customerUpdate = toCustomerUpdate(customerId, customerUpdateDTO);
        return toDTO(customerRepository.save(customerUpdate));
    }

    @Override
    @Transactional
    public CustomerDTO save(CustomerRequestDTO customerRequestDTO) {
        Customer customer = toEntity(customerRequestDTO);
        return toDTO(customerRepository.save(customer));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO findById(Integer customerId) {
        Customer customer = findByCustomerId(customerId);
        return toDTO(customer);
    }

    @Override
    @Transactional
    public void delete(Integer customerId) {
        Customer customer = findByCustomerId(customerId);
        customerRepository.delete(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findByCustomerId(Integer customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    private Customer toCustomerUpdate(Integer customerId, CustomerUpdateDTO customerUpdateDTO) {
        Customer customer = findByCustomerId(customerId);
        return customer.toBuilder()
                .name(customerUpdateDTO.getName())
                .phoneNumber(customerUpdateDTO.getPhoneNumber())
                .cnpj(customerUpdateDTO.getCnpj())
                .build();
    }


    private CustomerDTO toDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .cnpj(customer.getCnpj())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }

    private Customer toEntity(CustomerRequestDTO requestDTO) {
        return Customer.builder()
                .name(requestDTO.getName())
                .cnpj(requestDTO.getCnpj())
                .phoneNumber(requestDTO.getPhoneNumber())
                .build();
    }
}