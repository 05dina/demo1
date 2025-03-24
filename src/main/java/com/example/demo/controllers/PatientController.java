package com.example.demo.controllers;

import com.example.demo.infrastructure.entities.Patient;
import com.example.demo.services.interfaces.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {


    private final  PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @GetMapping
    public List<Patient> getPatient(){
        return patientService.findAll();
    }

    @PostMapping
    public Patient createPatient(@RequestBody  Patient patient){
        return patientService.save(patient);
    }

}
