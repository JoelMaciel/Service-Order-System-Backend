package com.joelmaciel.serviceorder.domain.services.impl;

import com.joelmaciel.serviceorder.api.dtos.response.TechnicianDTO;
import com.joelmaciel.serviceorder.domain.entities.Technician;
import com.joelmaciel.serviceorder.domain.excptions.TechnicianNotFoundException;
import com.joelmaciel.serviceorder.domain.repositories.TechnicianRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TechnicianServiceImplTest {

    public static final String TECHNICIAN_NOT_FOUND = "There is no such technician 100 registered in the database ";
    @Mock
    private TechnicianRepository technicianRepository;
    @InjectMocks
    private TechnicianServiceImpl technicianService;

    @Test
    @DisplayName("Given existing TechnicianId, When findById is called, Then return TechnicianDTO")
    void givenExistingTechnicianId_whenFindById_thenReturnTechnicianDTO() {
        Integer technicianId = 1;
        Technician technician = getMockTechnician();

        when(technicianRepository.findById(technicianId)).thenReturn(Optional.of(technician));

        TechnicianDTO technicianDTO = technicianService.findById(technicianId);

        assertNotNull(technicianDTO);
        assertEquals(technician.getId(), technicianDTO.getId());
        assertEquals(technician.getName(), technicianDTO.getName());
        assertEquals(technician.getCpf(), technicianDTO.getCpf());
        assertEquals(technician.getPhoneNumber(), technicianDTO.getPhoneNumber());

        verify(technicianRepository, times(1)).findById(technicianId);
    }

    @Test
    @DisplayName("Given non-existing TechnicianId, When findById is called, Then throw TechnicianNotFoundException")
    void givenNonExistingTechnicianId_whenFindById_thenThrowTechnicianNotFoundException() {
        Integer invalidTechnicianId = 100;

        when(technicianRepository.findById(invalidTechnicianId)).thenReturn(Optional.empty());

        TechnicianNotFoundException exception = assertThrows(TechnicianNotFoundException.class, () -> {
            technicianService.findById(invalidTechnicianId);
        });

        assertEquals(TECHNICIAN_NOT_FOUND, exception.getMessage());
        verify(technicianRepository, times(1)).findById(invalidTechnicianId);

    }

    private Technician getMockTechnician() {
        return Technician.builder()
                .id(1)
                .name("Paul")
                .cpf("501.114.620-02")
                .phoneNumber("(85) 988554433")
                .build();
    }

}