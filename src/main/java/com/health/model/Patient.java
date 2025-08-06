package com.health.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Patient extends User {
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private Set<Appointment> appointments;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private Set<MedicalRecord> medicalRecords;
    
    // Getters and Setters...
}