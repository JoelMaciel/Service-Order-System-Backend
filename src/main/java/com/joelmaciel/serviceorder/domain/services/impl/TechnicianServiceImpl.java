package com.joelmaciel.serviceorder.domain.services.impl;

import com.joelmaciel.serviceorder.api.dtos.request.TechnicianRequestDTO;
import com.joelmaciel.serviceorder.api.dtos.request.TechnicianUpdateDTO;
import com.joelmaciel.serviceorder.api.dtos.response.TechnicianDTO;
import com.joelmaciel.serviceorder.domain.entities.Technician;
import com.joelmaciel.serviceorder.domain.enums.Status;
import com.joelmaciel.serviceorder.domain.excptions.BusinessException;
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

    public static final String TECHNICIAN_IN_USE = "Technician has pending service order, cannot be deleted";
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
        Technician technician = toTechnicalUpdate(technicianId, technicianUpdateDTO);
        return toDTO(technicianRepository.save(technician));
    }

    @Override
    @Transactional
    public TechnicianDTO save(TechnicianRequestDTO technicianRequestDTO) {
        Technician technician = toEntity(technicianRequestDTO);
        return toDTO(technicianRepository.save(technician));
    }

    @Override
    @Transactional(readOnly = true)
    public TechnicianDTO findById(Integer technicianId) {
        Technician technician = findByTechnicianId(technicianId);
        return toDTO(technician);
    }

    @Override
    @Transactional
    public void delete(Integer technicianId) {
        Technician technician = findByTechnicianId(technicianId);
        if (technician.getOrderServiceList()
                .stream()
                .anyMatch(orderService -> orderService.getStatus() != Status.CLOSED)) {
            throw new BusinessException(TECHNICIAN_IN_USE);
        }
        technicianRepository.delete(technician);
    }

    @Override
    @Transactional(readOnly = true)
    public Technician findByTechnicianId(Integer technicianId) {
        return technicianRepository.findById(technicianId)
                .orElseThrow(() -> new TechnicianNotFoundException(technicianId));
    }

    private Technician toTechnicalUpdate(Integer technicianId, TechnicianUpdateDTO technicianUpdateDTO) {
        Technician existingTechnician = findByTechnicianId(technicianId);
        return existingTechnician.toBuilder()
                .name(technicianUpdateDTO.getName())
                .phoneNumber(technicianUpdateDTO.getPhoneNumber())
                .jobFunction(technicianUpdateDTO.getJobFunction())
                .build();
    }


    private TechnicianDTO toDTO(Technician technician) {
        return TechnicianDTO.builder()
                .id(technician.getId())
                .name(technician.getName())
                .cpf(technician.getCpf())
                .jobFunction(technician.getJobFunction())
                .phoneNumber(technician.getPhoneNumber())
                .build();
    }

    private Technician toEntity(TechnicianRequestDTO requestDTO) {
        return Technician.builder()
                .name(requestDTO.getName())
                .cpf(requestDTO.getCpf())
                .jobFunction(requestDTO.getJobFunction())
                .phoneNumber(requestDTO.getPhoneNumber())
                .build();
    }
}