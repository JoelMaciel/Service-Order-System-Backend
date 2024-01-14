package com.joelmaciel.serviceorder.domain.services.impl;

import com.joelmaciel.serviceorder.api.dtos.response.OrderServiceDTO;
import com.joelmaciel.serviceorder.domain.entities.Customer;
import com.joelmaciel.serviceorder.domain.entities.OrderService;
import com.joelmaciel.serviceorder.domain.entities.Technician;
import com.joelmaciel.serviceorder.domain.enums.Priority;
import com.joelmaciel.serviceorder.domain.enums.Status;
import com.joelmaciel.serviceorder.domain.excptions.OrderServiceNotFoundException;
import com.joelmaciel.serviceorder.domain.repositories.OrderServiceRepository;
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
}