package com.joelmaciel.serviceorder.domain.services.impl;

import com.joelmaciel.serviceorder.api.dtos.request.TechnicianUpdateDTO;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    @DisplayName("Given existing technicians, When findAll is called, Then return list of TechnicianDTOs")
    void givenExistingTechnicians_whenFindAll_thenReturnListOfTechnicianDTOs() {
        Technician mockTechnician = getMockTechnician();
        Technician mockTechnicianTwo = getMockTechnicianTwo();

        List<Technician> technicianList = Arrays.asList(
                mockTechnician, mockTechnicianTwo
        );

        when(technicianRepository.findAll()).thenReturn(technicianList);

        List<TechnicianDTO> technicianDTOList = technicianService.findAll();

        assertNotNull(technicianDTOList);
        assertEquals(technicianList.size(), technicianDTOList.size());

        for (int i = 0; i < technicianList.size(); i++) {
            TechnicianDTO technicianDTO = technicianDTOList.get(i);
            Technician technician = technicianList.get(i);

            assertEquals(technician.getId(), technicianDTO.getId());
            assertEquals(technician.getName(), technicianDTO.getName());
            assertEquals(technician.getCpf(), technicianDTO.getCpf());
            assertEquals(technician.getPhoneNumber(), technicianDTO.getPhoneNumber());
        }

        verify(technicianRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Given no existing technicians, When findAll is called, Then return an empty list")
    void givenNoExistingTechnicians_whenFindAll_thenReturnEmptyList() {
        when(technicianRepository.findAll()).thenReturn(Collections.emptyList());

        List<TechnicianDTO> technicianDTOList = technicianService.findAll();

        assertNotNull(technicianDTOList);
        assertTrue(technicianDTOList.isEmpty());

        verify(technicianRepository, times(1)).findAll();
    }

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

    @Test
    @DisplayName("Given existing TechnicianId and valid update data, When update is called, Then return updated TechnicianDTO")
    void givenExistingTechnicianIdAndValidUpdateData_whenUpdate_thenReturnUpdatedTechnicianDTO() {
        Integer technicianId = 1;
        TechnicianUpdateDTO technicianUpdateDTO = getMockTechnicianUpdateDTO();
        Technician existingTechnician = getMockTechnician();
        Technician updatedTechnician = existingTechnician.toBuilder()
                .phoneNumber(technicianUpdateDTO.getPhoneNumber())
                .build();

        when(technicianRepository.findById(technicianId)).thenReturn(Optional.of(existingTechnician));
        when(technicianRepository.save(updatedTechnician)).thenReturn(updatedTechnician);

        TechnicianDTO updatedTechnicianDTO = technicianService.update(technicianId, technicianUpdateDTO);

        assertNotNull(updatedTechnicianDTO);
        assertEquals(updatedTechnician.getId(), updatedTechnicianDTO.getId());
        assertEquals(updatedTechnician.getName(), updatedTechnicianDTO.getName());
        assertEquals(updatedTechnician.getCpf(), updatedTechnicianDTO.getCpf());
        assertEquals(updatedTechnician.getPhoneNumber(), updatedTechnicianDTO.getPhoneNumber());

        verify(technicianRepository, times(1)).findById(technicianId);
        verify(technicianRepository, times(1)).save(updatedTechnician);
    }

    @Test
    @DisplayName("Given non-existing TechnicianId, When update is called, Then throw TechnicianNotFoundException")
    void givenNonExistingTechnicianId_whenUpdate_thenThrowTechnicianNotFoundException() {
        Integer invalidTechnicianId = 100;
        TechnicianUpdateDTO technicianUpdateDTO = getMockTechnicianUpdateDTO();

        when(technicianRepository.findById(invalidTechnicianId)).thenReturn(Optional.empty());

        TechnicianNotFoundException exception = assertThrows(TechnicianNotFoundException.class, () -> {
            technicianService.update(invalidTechnicianId, technicianUpdateDTO);
        });

        assertEquals(TECHNICIAN_NOT_FOUND, exception.getMessage());
        verify(technicianRepository, times(1)).findById(invalidTechnicianId);
        verify(technicianRepository, never()).save(any(Technician.class));
    }


    private Technician getMockTechnician() {
        return Technician.builder()
                .id(1)
                .name("Paul")
                .cpf("501.114.620-02")
                .phoneNumber("(85) 988554433")
                .build();
    }
    private Technician getMockTechnicianTwo() {
        return Technician.builder()
                .id(2)
                .name("Joe")
                .cpf("141.114.620-15")
                .phoneNumber("(85) 988554452")
                .build();
    }
    private TechnicianUpdateDTO getMockTechnicianUpdateDTO() {
        return TechnicianUpdateDTO.builder()
                .phoneNumber("(85) 987654321")
                .build();
    }
}