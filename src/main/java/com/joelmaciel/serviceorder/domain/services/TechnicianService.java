package com.joelmaciel.serviceorder.domain.services;

import com.joelmaciel.serviceorder.api.dtos.response.TechnicianDTO;
import com.joelmaciel.serviceorder.domain.entities.Technician;

public interface TechnicianService {
    TechnicianDTO findById(Integer technicianId);
    Technician findByTechnicianId(Integer technicianId);
}
