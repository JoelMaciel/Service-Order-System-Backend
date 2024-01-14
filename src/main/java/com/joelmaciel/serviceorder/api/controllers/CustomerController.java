package com.joelmaciel.serviceorder.api.controllers;

import com.joelmaciel.serviceorder.api.dtos.request.CustomerRequestDTO;
import com.joelmaciel.serviceorder.api.dtos.request.CustomerUpdateDTO;
import com.joelmaciel.serviceorder.api.dtos.response.CustomerDTO;
import com.joelmaciel.serviceorder.domain.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getAll() {
        return customerService.findAll();
    }

    @GetMapping("/{customerId}")
    public CustomerDTO getOne(@PathVariable Integer customerId) {
        return customerService.findById(customerId);
    }

    @PatchMapping("/{customerId}")
    public CustomerDTO update(
            @PathVariable Integer customerId,
            @RequestBody @Valid CustomerUpdateDTO customerUpdateDTO
    ) {
        return customerService.update(customerId, customerUpdateDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO save(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        return customerService.save(customerRequestDTO);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  delete(@PathVariable Integer customerId) {
        customerService.delete(customerId);
    }


}
