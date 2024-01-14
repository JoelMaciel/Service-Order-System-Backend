package com.joelmaciel.serviceorder.domain.repositories;

import com.joelmaciel.serviceorder.domain.entities.OrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderServiceRepository extends JpaRepository<OrderService, Integer> {
}
