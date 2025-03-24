package com.example.demo.controllers;

import com.example.demo.infrastructure.entities.Patient;
import com.example.demo.services.interfaces.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    void getPatientReturnsListOfPatients() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setEmail("john@example.com");
        List<Patient> patients = Collections.singletonList(patient);
        when(patientService.findAll()).thenReturn(patients);

        mockMvc.perform(get("/patient"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"firstName\":\"John\",\"email\":\"john@example.com\"}]"));
    }



    @Test
    void createPatientReturnsCreatedPatient() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("Jane");
        patient.setLastName("Doe");
        patient.setEmail("jane@example.com");
        when(patientService.save(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Jane\",\"email\":\"jane@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"firstName\":\"Jane\",\"email\":\"jane@example.com\"}"));
    }
}