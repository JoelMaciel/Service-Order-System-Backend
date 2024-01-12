package com.joelmaciel.serviceorder.domain.repositories;

import com.joelmaciel.serviceorder.domain.entities.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Integer> {
}
