package com.joelmaciel.serviceorder.domain.services.impl;

import com.joelmaciel.serviceorder.api.dtos.request.OrderServiceRequestDTO;
import com.joelmaciel.serviceorder.api.dtos.response.OrderServiceDTO;
import com.joelmaciel.serviceorder.domain.entities.Customer;
import com.joelmaciel.serviceorder.domain.entities.OrderService;
import com.joelmaciel.serviceorder.domain.entities.Technician;
import com.joelmaciel.serviceorder.domain.excptions.OrderServiceNotFoundException;
import com.joelmaciel.serviceorder.domain.repositories.OrderServiceRepository;
import com.joelmaciel.serviceorder.domain.services.CustomerService;
import com.joelmaciel.serviceorder.domain.services.OrderServiceService;
import com.joelmaciel.serviceorder.domain.services.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceServiceImpl implements OrderServiceService {

    public static final String ORDER_SERVICE_NOT_FOUND = "Service Order not registered in the database";
    private final OrderServiceRepository orderServiceRepository;
    private final TechnicianService technicianService;
    private final CustomerService customerService;

    @Override
    @Transactional(readOnly = true)
    public List<OrderServiceDTO> findAll() {
        return orderServiceRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderServiceDTO save(OrderServiceRequestDTO orderServiceRequestDTO) {
        Customer customer = customerService.findByCustomerId(orderServiceRequestDTO.getCustomer());
        Technician technician = technicianService.findByTechnicianId(orderServiceRequestDTO.getTechnician());

        OrderService orderService = toEntity(orderServiceRequestDTO);
        orderService.setCustomer(customer);
        orderService.setTechnician(technician);

        return toDTO(orderServiceRepository.save(orderService));
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

    private OrderService toEntity(OrderServiceRequestDTO requestDTO) {
        return OrderService.builder()
                .status(requestDTO.getStatus())
                .priority(requestDTO.getPriority())
                .observation(requestDTO.getObservation())
                .openingDate(OffsetDateTime.now())
                .closingDate(requestDTO.getClosingDate())
                .build();
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
