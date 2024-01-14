package com.joelmaciel.serviceorder.api.controllers;

import com.joelmaciel.serviceorder.api.dtos.response.OrderServiceDTO;
import com.joelmaciel.serviceorder.domain.services.OrderServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orderServices")
public class OrderServiceController {

    private final OrderServiceService orderServiceService;

    @GetMapping
    public List<OrderServiceDTO> getAll() {
        return orderServiceService.findAll();
    }

    @GetMapping("/{orderServiceId}")
    public OrderServiceDTO getOne(@PathVariable Integer orderServiceId) {
        return orderServiceService.findById(orderServiceId);
    }
}
