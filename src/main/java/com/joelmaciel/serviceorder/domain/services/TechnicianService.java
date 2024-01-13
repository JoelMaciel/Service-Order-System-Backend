package com.joelmaciel.serviceorder.domain.services;

import com.joelmaciel.serviceorder.api.dtos.request.TechnicianRequestDTO;
import com.joelmaciel.serviceorder.api.dtos.request.TechnicianUpdateDTO;
import com.joelmaciel.serviceorder.api.dtos.response.TechnicianDTO;
import com.joelmaciel.serviceorder.domain.entities.Technician;

import java.util.List;

public interface TechnicianService {
    TechnicianDTO findById(Integer technicianId);
    Technician findByTechnicianId(Integer technicianId);

    List<TechnicianDTO> findAll();

    TechnicianDTO update(Integer technicianId, TechnicianUpdateDTO technicianUpdateDTOO);

    TechnicianDTO save(TechnicianRequestDTO technicianRequestDTO);
}
