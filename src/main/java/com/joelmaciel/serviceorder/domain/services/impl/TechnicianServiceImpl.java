package com.joelmaciel.serviceorder.domain.services.impl;

import com.joelmaciel.serviceorder.api.dtos.request.TechnicianUpdateDTO;
import com.joelmaciel.serviceorder.api.dtos.response.TechnicianDTO;
import com.joelmaciel.serviceorder.domain.entities.Technician;
import com.joelmaciel.serviceorder.domain.excptions.TechnicianNotFoundException;
import com.joelmaciel.serviceorder.domain.repositories.TechnicianRepository;
import com.joelmaciel.serviceorder.domain.services.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnicianServiceImpl implements TechnicianService {

    private final TechnicianRepository technicianRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TechnicianDTO> findAll() {
        List<Technician> technicianList = technicianRepository.findAll();
        return technicianList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TechnicianDTO update(Integer technicianId, TechnicianUpdateDTO technicianUpdateDTO) {
        Technician technician = findByTechnicianId(technicianId);
        technician.setPhoneNumber(technicianUpdateDTO.getPhoneNumber());
        return toDTO(technicianRepository.save(technician));
    }

    @Override
    @Transactional(readOnly = true)
    public TechnicianDTO findById(Integer technicianId) {
        Technician technician = findByTechnicianId(technicianId);
        return toDTO(technician);
    }

    @Override
    @Transactional(readOnly = true)
    public Technician findByTechnicianId(Integer technicianId) {
        return technicianRepository.findById(technicianId)
                .orElseThrow(() -> new TechnicianNotFoundException(technicianId));
    }

    private TechnicianDTO toDTO(Technician technician) {
        return TechnicianDTO.builder()
                .id(technician.getId())
                .name(technician.getName())
                .cpf(technician.getCpf())
                .phoneNumber(technician.getPhoneNumber())
                .build();
    }
}