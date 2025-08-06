package com.health.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @SuppressWarnings("unused")
	private String diagnosis;
    @SuppressWarnings("unused")
	private String treatment;
    @SuppressWarnings("unused")
	private LocalDateTime recordDate;
    
    // Getters and Setters...
}