package com.joelmaciel.serviceorder.domain.excptions;

public class TechnicianNotFoundException extends ResourceNotFoundException {
    public TechnicianNotFoundException(String message) {
        super(message);
    }

    public TechnicianNotFoundException(Integer technicianId) {
        this(String.format("There is no such technician %d registered in the database ", technicianId));
    }
}
