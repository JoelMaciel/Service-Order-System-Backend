package com.joelmaciel.serviceorder.api.controllers;

import com.joelmaciel.serviceorder.api.dtos.request.TechnicianRequestDTO;
import com.joelmaciel.serviceorder.api.dtos.request.TechnicianUpdateDTO;
import com.joelmaciel.serviceorder.api.dtos.response.TechnicianDTO;
import com.joelmaciel.serviceorder.domain.services.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PatchMapping("/{technicianId}")
    public TechnicianDTO update(
            @PathVariable Integer technicianId,
            @RequestBody @Valid TechnicianUpdateDTO technicianUpdateDTOO
    ) {
        return technicianService.update(technicianId, technicianUpdateDTOO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TechnicianDTO save(@RequestBody @Valid TechnicianRequestDTO technicianRequestDTO) {
        return technicianService.save(technicianRequestDTO);
    }

    @DeleteMapping("/{technicianId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  delete(@PathVariable Integer technicianId) {
        technicianService.delete(technicianId);
    }


}
