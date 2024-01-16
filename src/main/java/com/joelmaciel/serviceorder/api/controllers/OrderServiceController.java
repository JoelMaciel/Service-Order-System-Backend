package com.joelmaciel.serviceorder.api.controllers;

import com.joelmaciel.serviceorder.api.dtos.request.OrderServiceRequestDTO;
import com.joelmaciel.serviceorder.api.dtos.response.OrderServiceDTO;
import com.joelmaciel.serviceorder.domain.services.OrderServiceService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderServiceDTO create(@RequestBody @Valid OrderServiceRequestDTO orderServiceRequestDTO) {
        return orderServiceService.save(orderServiceRequestDTO);
    }
}
