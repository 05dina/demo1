package com.example.demo.services.interfaces;

import com.example.demo.infrastructure.entities.Patient;

import java.util.List;

public interface PatientI {
    Patient save(Patient patient);
    List<Patient> findAll();

}
