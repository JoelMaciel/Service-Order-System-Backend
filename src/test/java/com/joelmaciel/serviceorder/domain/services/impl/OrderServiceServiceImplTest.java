package com.joelmaciel.serviceorder.domain.services.impl;

import com.joelmaciel.serviceorder.api.dtos.request.OrderServiceRequestDTO;
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
import org.springframework.dao.DataIntegrityViolationException;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
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
    @DisplayName("Given existing order service, When findById is called, Then return OrderServiceDTO")
    void givenExistingOrderService_whenFindById_thenReturnOrderServiceDTO() {
        when(orderServiceRepository.findById(orderService.getId())).thenReturn(Optional.of(orderService));

        OrderServiceDTO orderServiceDTO = orderServiceService.findById(orderService.getId());

        assertNotNull(orderServiceDTO);
        assertEquals(orderService.getId(), orderServiceDTO.getId());
        assertEquals(orderService.getOpeningDate(), orderServiceDTO.getOpeningDate());
        assertEquals(orderService.getClosingDate(), orderServiceDTO.getClosingDate());
        assertEquals(orderService.getPriority(), orderServiceDTO.getPriority());
        assertEquals(orderService.getObservation(), orderServiceDTO.getObservation());
        assertEquals(orderService.getStatus(), orderServiceDTO.getStatus());
        assertEquals(orderService.getTechnician().getId(), orderServiceDTO.getTechnician());
        assertEquals(orderService.getCustomer().getId(), orderServiceDTO.getCustomer());

        verify(orderServiceRepository, times(1)).findById(orderService.getId());
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
    @DisplayName("Given existing order services, When findAll is called, Then return a list of OrderServiceDTOs")
    void givenExistingOrderServices_whenFindAll_thenReturnListOfOrderServiceDTOs() {
        List<OrderService> mockOrderServices = Arrays.asList(orderService, getAnotherMockOrderService());
        when(orderServiceRepository.findAll()).thenReturn(mockOrderServices);

        List<OrderServiceDTO> orderServiceDTOList = orderServiceService.findAll();

        assertNotNull(orderServiceDTOList);
        assertEquals(mockOrderServices.size(), orderServiceDTOList.size());

        for (int i = 0; i < mockOrderServices.size(); i++) {
            OrderServiceDTO orderServiceDTO = orderServiceDTOList.get(i);
            OrderService mockOrderService = mockOrderServices.get(i);

            assertEquals(mockOrderService.getId(), orderServiceDTO.getId());
            assertEquals(mockOrderService.getOpeningDate(), orderServiceDTO.getOpeningDate());
            assertEquals(mockOrderService.getClosingDate(), orderServiceDTO.getClosingDate());
            assertEquals(mockOrderService.getPriority(), orderServiceDTO.getPriority());
            assertEquals(mockOrderService.getObservation(), orderServiceDTO.getObservation());
            assertEquals(mockOrderService.getStatus(), orderServiceDTO.getStatus());
            assertEquals(mockOrderService.getTechnician().getId(), orderServiceDTO.getTechnician());
            assertEquals(mockOrderService.getCustomer().getId(), orderServiceDTO.getCustomer());
        }

        verify(orderServiceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Given valid order service request, When save is called, Then return OrderServiceDTO")
    void givenValidOrderServiceRequest_whenSave_thenReturnOrderServiceDTO() {
        Customer mockCustomer = getMockCustomer();
        Technician technician = getMockTechnician();
        OrderServiceRequestDTO mocOrderServiceRequestDTO = getMockOrderServiceRequestDTO();
        when(customerService.findByCustomerId(anyInt())).thenReturn(mockCustomer);
        when(technicianService.findByTechnicianId(anyInt())).thenReturn(technician);
        when(orderServiceRepository.save(any(OrderService.class))).thenReturn(orderService);

        OrderServiceDTO resultDTO = orderServiceService.save(mocOrderServiceRequestDTO);

        assertNotNull(resultDTO);
        assertEquals(orderService.getId(), resultDTO.getId());
        assertEquals(orderService.getOpeningDate(), resultDTO.getOpeningDate());
        assertEquals(orderService.getClosingDate(), resultDTO.getClosingDate());
        assertEquals(orderService.getPriority(), resultDTO.getPriority());
        assertEquals(orderService.getObservation(), resultDTO.getObservation());
        assertEquals(orderService.getStatus(), resultDTO.getStatus());
        verify(orderServiceRepository, times(1)).save(any(OrderService.class));
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
                .cpf("501.114.620-02")
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

    private void getMockOrderService() {
        orderService = OrderService.builder()
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