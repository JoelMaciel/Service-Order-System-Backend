package com.joelmaciel.serviceorder.domain.services.impl;

import com.joelmaciel.serviceorder.api.dtos.response.OrderServiceDTO;
import com.joelmaciel.serviceorder.domain.entities.OrderService;
import com.joelmaciel.serviceorder.domain.excptions.OrderServiceNotFoundException;
import com.joelmaciel.serviceorder.domain.repositories.OrderServiceRepository;
import com.joelmaciel.serviceorder.domain.services.OrderServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceServiceImpl implements OrderServiceService {

    public static final String ORDER_SERVICE_NOT_FOUND = "Service Order not registered in the database";
    private final OrderServiceRepository orderServiceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrderServiceDTO> findAll() {
        return orderServiceRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderServiceDTO findById(Integer orderServiceId) {
        OrderService orderService = findByOrderServiceId(orderServiceId);
        return toDTO(orderService);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderService findByOrderServiceId(Integer orderServiceId) {
        return orderServiceRepository.findById(orderServiceId)
                .orElseThrow(() -> new OrderServiceNotFoundException(ORDER_SERVICE_NOT_FOUND));
    }

    private OrderServiceDTO toDTO(OrderService orderService) {
        return OrderServiceDTO.builder()
                .id(orderService.getId())
                .priority(orderService.getPriority())
                .observation(orderService.getObservation())
                .status(orderService.getStatus())
                .technician(orderService.getTechnician().getId())
                .customer(orderService.getCustomer().getId())
                .openingDate((orderService.getOpeningDate()))
                .closingDate(orderService.getClosingDate())
                .build();
    }
}
