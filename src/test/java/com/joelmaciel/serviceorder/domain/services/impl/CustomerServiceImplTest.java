package com.joelmaciel.serviceorder.domain.services.impl;

import com.joelmaciel.serviceorder.api.dtos.request.CustomerRequestDTO;
import com.joelmaciel.serviceorder.api.dtos.request.CustomerUpdateDTO;
import com.joelmaciel.serviceorder.api.dtos.response.CustomerDTO;
import com.joelmaciel.serviceorder.domain.entities.Customer;
import com.joelmaciel.serviceorder.domain.excptions.CustomerNotFoundException;
import com.joelmaciel.serviceorder.domain.repositories.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    public static final String CUSTOMER_NOT_FOUND = "There is no such customer 100 registered in the database ";
    public static final String CPF_IN_USE = "There is already a customer registered with this CPF";
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    @DisplayName("Given existing customer, When findAll is called, Then return list of CustomersDTOs")
    void givenExistingCustomer_whenFindAll_thenReturnListOfCustomersDTOs() {
        Customer mockCustomer = getMockCustomer();
        Customer mockCustomerTwo = getMockCustomerTwo();

        List<Customer> customerList = Arrays.asList(
                mockCustomer, mockCustomerTwo
        );

        when(customerRepository.findAll()).thenReturn(customerList);

        List<CustomerDTO> customerDTOList = customerService.findAll();

        assertNotNull(customerList);
        assertEquals(customerList.size(), customerDTOList.size());

        for (int i = 0; i < customerList.size(); i++) {
            CustomerDTO customerDTO = customerDTOList.get(i);
            Customer customer = customerList.get(i);

            assertEquals(customer.getId(), customerDTO.getId());
            assertEquals(customer.getName(), customerDTO.getName());
            assertEquals(customer.getCnpj(), customerDTO.getCnpj());
            assertEquals(customer.getPhoneNumber(), customerDTO.getPhoneNumber());
        }

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Given no existing customers, When findAll is called, Then return an empty list")
    void givenNoExistingCustomers_whenFindAll_thenReturnEmptyList() {
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        List<CustomerDTO> customerDTOList = customerService.findAll();

        assertNotNull(customerDTOList);
        assertTrue(customerDTOList.isEmpty());

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Given existing CustomerId, When findById is called, Then return CustomerDTO")
    void givenExistingCustomerId_whenFindById_thenReturnCustomerDTO() {
        Integer customerId = 1;
        Customer customer = getMockCustomer();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.findById(customerId);

        assertNotNull(customerDTO);
        assertEquals(customer.getId(), customerDTO.getId());
        assertEquals(customer.getName(), customerDTO.getName());
        assertEquals(customer.getCnpj(), customerDTO.getCnpj());
        assertEquals(customer.getPhoneNumber(), customerDTO.getPhoneNumber());

        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    @DisplayName("Given non-existing CustomerId, When findById is called, Then throw CustomerNotFoundException")
    void givenNonExistingCustomerId_whenFindById_thenThrowCustomerNotFoundException() {
        Integer invalidCustomerId = 100;

        when(customerRepository.findById(invalidCustomerId)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.findById(invalidCustomerId);
        });

        assertEquals(CUSTOMER_NOT_FOUND, exception.getMessage());
        verify(customerRepository, times(1)).findById(invalidCustomerId);

    }

    @Test
    @DisplayName("Given existing CustomerId and valid update data, When update is called, Then return updated CustomerDTO")
    void givenExistingCustomerIdAndValidUpdateData_whenUpdate_thenReturnUpdatedCustomerDTO() {
        Integer customerId = 1;
        CustomerUpdateDTO customerUpdateDTO = getMockCustomerUpdateDTO();
        Customer existingCustomer = getMockCustomer();
        Customer updatedCustomer = existingCustomer.toBuilder()
                .phoneNumber(customerUpdateDTO.getPhoneNumber())
                .build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);

        CustomerDTO customerDTO = customerService.update(customerId, customerUpdateDTO);

        assertNotNull(customerDTO);
        assertEquals(updatedCustomer.getId(), customerDTO.getId());
        assertEquals(updatedCustomer.getName(), customerDTO.getName());
        assertEquals(updatedCustomer.getCnpj(), customerDTO.getCnpj());
        assertEquals(updatedCustomer.getPhoneNumber(), customerDTO.getPhoneNumber());

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).save(updatedCustomer);
    }

    @Test
    @DisplayName("Given non-existing CustomerId, When update is called, Then throw CustomerNotFoundException")
    void givenNonExistingCustomerId_whenUpdate_thenThrowTCustomerNotFoundException() {
        Integer invalidTCustomerId = 100;
        CustomerUpdateDTO customerUpdateDTO = getMockCustomerUpdateDTO();

        when(customerRepository.findById(invalidTCustomerId)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.update(invalidTCustomerId, customerUpdateDTO);
        });

        assertEquals(CUSTOMER_NOT_FOUND, exception.getMessage());
        verify(customerRepository, times(1)).findById(invalidTCustomerId);
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Given valid CustomerRequestDTO, When save is called, Then return saved CustomerDTO")
    void givenValidCustomerRequestDTO_whenSave_thenReturnSavedCustomerDTO() {
        CustomerRequestDTO customerRequestDTO = getCustomerRequestDTO();
        Customer customer = getCustomer();

        when(customerRepository.save(any(Customer.class))).thenAnswer(i -> i.getArguments()[0]);

        CustomerDTO savedCustomerDTO = customerService.save(customerRequestDTO);

        assertNotNull(savedCustomerDTO);
        assertEquals(customer.getName(), savedCustomerDTO.getName());
        assertEquals(customer.getCnpj(), savedCustomerDTO.getCnpj());
        assertEquals(customer.getPhoneNumber(), savedCustomerDTO.getPhoneNumber());

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("Given existing CPF, When save is called, Then throw DataIntegrityViolationException")
    void givenExistingCPF_whenSave_thenThrowDataIntegrityViolationException() {
        CustomerRequestDTO customerRequestDTO = CustomerRequestDTO.builder()
                .name("John")
                .cnpj("123.456.789-00")
                .phoneNumber("(11) 98888-8888")
                .build();

        when(customerRepository.save(any(Customer.class))).thenThrow(
                new DataIntegrityViolationException(CPF_IN_USE)
        );

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            customerService.save(customerRequestDTO);
        });

        assertEquals(CustomerServiceImplTest.CPF_IN_USE, exception.getMessage());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("Given existing customerId, When delete is called, Then Customer is deleted")
    void givenExistingCustomerId_whenDelete_thenCustomerIsDeleted() {
        Integer customerId = 1;
        Customer customer = getCustomer();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        customerService.delete(customerId);
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    @DisplayName("Given non-existing customerId, When delete is called, Then throw CustomerNotFoundException")
    void givenNonExistingCustomerId_whenDelete_thenThrowCustomerNotFoundException() {
        Integer invalidCustomerId = 100;
        Customer customer = getCustomer();

        when(customerRepository.findById(invalidCustomerId)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                () -> customerService.delete(invalidCustomerId)
        );

        assertEquals(CUSTOMER_NOT_FOUND, exception.getMessage());
        verify(customerRepository, never()).delete(customer);
    }

    private static CustomerRequestDTO getCustomerRequestDTO() {
        return CustomerRequestDTO.builder()
                .name("John")
                .cnpj("123.456.789-00")
                .phoneNumber("(11) 98888-8888")
                .build();
    }

    private static Customer getCustomer() {
        return Customer.builder()
                .id(1)
                .name("John")
                .cnpj("123.456.789-00")
                .phoneNumber("(11) 98888-8888")
                .build();
    }

    private Customer getMockCustomer() {
        return Customer.builder()
                .id(1)
                .name("Paul")
                .cnpj("501.114.620-02")
                .phoneNumber("(85) 988554433")
                .build();
    }

    private Customer getMockCustomerTwo() {
        return Customer.builder()
                .id(2)
                .name("Joe")
                .cnpj("141.114.620-15")
                .phoneNumber("(85) 988554452")
                .build();
    }

    private CustomerUpdateDTO getMockCustomerUpdateDTO() {
        return CustomerUpdateDTO.builder()
                .phoneNumber("(85) 987654321")
                .build();
    }
}