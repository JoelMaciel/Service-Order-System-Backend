package com.joelmaciel.serviceorder.api.controllers;

import com.joelmaciel.serviceorder.api.dtos.response.TechnicianDTO;
import com.joelmaciel.serviceorder.domain.services.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/technicians")
public class TechnicianController {

    private final TechnicianService technicianService;

    @GetMapping
    public List<TechnicianDTO> getAll() {
        return technicianService.findAll();
    }

    @GetMapping("/{technicianId}")
    public TechnicianDTO getOne(@PathVariable Integer technicianId) {
        return technicianService.findById(technicianId);
    }
}
