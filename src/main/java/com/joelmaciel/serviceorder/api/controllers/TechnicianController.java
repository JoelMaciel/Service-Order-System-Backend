package com.joelmaciel.serviceorder.api.controllers;

import com.joelmaciel.serviceorder.api.dtos.response.TechnicianDTO;
import com.joelmaciel.serviceorder.domain.services.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/technicians")
public class TechnicianController {

    private final TechnicianService technicianService;

    @GetMapping("/{technicianId}")
    public TechnicianDTO getOne(@PathVariable Integer technicianId) {
        return technicianService.findById(technicianId);
    }
}
