package com.example.demo.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "patient")
public class Patient {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_id_gen")
    @SequenceGenerator(name = "patient_id_gen", sequenceName = "patient_seq", allocationSize = 1)
    @Column(name = "patient_id", nullable = false)
    private Long patientId;


    @Column(name = "number", nullable = false)
    private Long id;


    @Column(name = "identification_type", nullable = false, length = 2)
    private String identificationType;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "address", nullable = false, length = 250)
    private String address;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "mobile_number", nullable = false, length = 50)
    private String mobileNumber;

}