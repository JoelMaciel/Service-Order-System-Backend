package com.joelmaciel.serviceorder.domain.services;

import com.joelmaciel.serviceorder.api.dtos.response.OrderServiceDTO;
import com.joelmaciel.serviceorder.domain.entities.OrderService;

import java.util.List;

public interface OrderServiceService {

    OrderServiceDTO findById(Integer orderServiceId);

    OrderService findByOrderServiceId(Integer orderServiceId);


    List<OrderServiceDTO> findAll();
}
