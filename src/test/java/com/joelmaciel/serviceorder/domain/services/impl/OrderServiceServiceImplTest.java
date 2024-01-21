package com.joelmaciel.serviceorder.domain.services.impl;

import com.joelmaciel.serviceorder.api.dtos.request.OrderServiceRequestDTO;
import com.joelmaciel.serviceorder.api.dtos.request.OrderServiceRequestUpdateDTO;
import com.joelmaciel.serviceorder.api.dtos.response.OrderServiceDTO;
import com.joelmaciel.serviceorder.domain.entities.Customer;
import com.joelmaciel.serviceorder.domain.entities.OrderService;
import com.joelmaciel.serviceorder.domain.entities.Technician;
import com.joelmaciel.serviceorder.domain.enums.Priority;
import com.joelmaciel.serviceorder.domain.enums.Status;
import com.joelmaciel.serviceorder.domain.excptions.CustomerNotFoundException;
import com.joelmaciel.serviceorder.domain.excptions.OrderServiceNotFoundException;
import com.joelmaciel.serviceorder.domain.repositories.CustomerRepository;
import com.joelmaciel.serviceorder.domain.repositories.OrderServiceRepository;
import com.joelmaciel.serviceorder.domain.repositories.TechnicianRepository;
import com.joelmaciel.serviceorder.domain.services.CustomerService;
import com.joelmaciel.serviceorder.domain.services.TechnicianService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceServiceImplTest {

    public static final String SERVICE_ORDER_NOT_FOUND = "Service Order not registered in the database";
    @Mock
    private OrderServiceRepository orderServiceRepository;
    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    TechnicianRepository technicianRepository;
    @Mock
    private TechnicianService technicianService;
    @InjectMocks
    private OrderServiceServiceImpl orderServiceService;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        getMockOrderService();
    }

    @Test
    @DisplayName("Given non-existing order service, When findById is called, Then throw OrderServiceNotFoundException")
    void givenNonExistingOrderService_whenFindById_thenThrowOrderServiceNotFoundException() {
        when(orderServiceRepository.findById(2)).thenReturn(Optional.empty());

        OrderServiceNotFoundException exception = assertThrows(OrderServiceNotFoundException.class, () -> {
            orderServiceService.findById(2);
        });

        assertEquals(SERVICE_ORDER_NOT_FOUND, exception.getMessage());

        verify(orderServiceRepository, times(1)).findById(2);
    }

    @Test
    @DisplayName("Given invalid customer ID, When save is called, Then throw CustomerNotFoundException")
    void givenInvalidCustomerId_whenSave_thenThrowCustomerNotFoundException() {
        OrderServiceRequestDTO requestDTO = getMockOrderServiceRequestDTO();
        when(customerService.findByCustomerId(anyInt())).thenThrow(new CustomerNotFoundException("Customer with ID not found"));

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            orderServiceService.save(requestDTO);
        });
        assertEquals("Customer with ID not found", exception.getMessage());
        verify(customerService, times(1)).findByCustomerId(anyInt());
    }

    @Test
    @DisplayName("Given an existing order service, When the update method is called, Then it returns OrderServiceDTO")
    void givenExistingOrderService_whenUpdateMethodIsCalled_thenReturnsOrderServiceDTO() {
        OrderService mockOrderService = getMockOrderService();
        OrderServiceRequestUpdateDTO updateDTO = getMockOrderServiceRequestUpdateDTO();

        when(orderServiceRepository.findById(1)).thenReturn(Optional.ofNullable(mockOrderService));
        when(technicianService.findByTechnicianId(anyInt())).thenReturn(getMockTechnician());
        when(orderServiceRepository.save(any(OrderService.class))).thenReturn(mockOrderService);

        OrderServiceDTO resultDTO = orderServiceService.update(1, updateDTO);

        assertNotNull(resultDTO);
        assertEquals(mockOrderService.getId(), resultDTO.getId());
        assertEquals(mockOrderService.getOpeningDate(), resultDTO.getOpeningDate());
        assertEquals(mockOrderService.getClosingDate(), resultDTO.getClosingDate());
        assertEquals(mockOrderService.getPriority(), resultDTO.getPriority());
        assertEquals(mockOrderService.getObservation(), resultDTO.getObservation());
        assertEquals(mockOrderService.getStatus(), resultDTO.getStatus());

        verify(orderServiceRepository, times(1)).findById(anyInt());
        verify(orderServiceRepository, times(1)).save(any(OrderService.class));
    }

    @Test
    @DisplayName("Given a non-existing service order, When the update method is called, Then it throws OrderServiceNotFoundException")
    void givenNonExistingServiceOrder_whenUpdateMethodIsCalled_thenThrowsOrderServiceNotFoundException() {
        OrderServiceRequestUpdateDTO updateDTO = getMockOrderServiceRequestUpdateDTO();
        when(orderServiceRepository.findById(1)).thenReturn(Optional.empty());

        OrderServiceNotFoundException exception = assertThrows(OrderServiceNotFoundException.class, () -> {
            orderServiceService.update(1, updateDTO);
        });

        assertEquals("Service Order not registered in the database", exception.getMessage());
        verify(orderServiceRepository, times(1)).findById(anyInt());
        verify(technicianService, never()).findByTechnicianId(anyInt());
        verify(orderServiceRepository, never()).save(any(OrderService.class));
    }

    private OrderServiceRequestUpdateDTO getMockOrderServiceRequestUpdateDTO() {
        return OrderServiceRequestUpdateDTO.builder()
                .status(Status.IN_PROGRESS)
                .priority(Priority.MEDIUM)
                .observation("Client want fast")
                .technician(1)
                .build();
    }

    private OrderServiceRequestDTO getMockOrderServiceRequestDTO() {
        return OrderServiceRequestDTO.builder()
                .status(Status.IN_PROGRESS)
                .priority(Priority.MEDIUM)
                .observation("Client want fast")
                .technician(1)
                .customer(1)
                .closingDate(null)
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

    private Technician getMockTechnician() {
        return Technician.builder()
                .id(1)
                .name("Paul")
                .cpf("501.114.620-02")
                .phoneNumber("(85) 988554433")
                .build();
    }

    private OrderService getMockOrderService() {
        return OrderService.builder()
                .id(1)
                .priority(Priority.MEDIUM)
                .observation("Observation Teste")
                .status(Status.OPEN)
                .technician(new Technician())
                .customer(new Customer())
                .openingDate(OffsetDateTime.now())
                .closingDate(null)
                .build();
    }

    private OrderService getAnotherMockOrderService() {
        return OrderService.builder()
                .id(2)
                .priority(Priority.HIGH)
                .observation("Another Test Observation")
                .status(Status.IN_PROGRESS)
                .technician(new Technician())
                .customer(new Customer())
                .openingDate(OffsetDateTime.now())
                .closingDate(OffsetDateTime.now().plusDays(1))
                .build();
    }
}