package com.health.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Doctor extends User {
    private String specialty;
    private String availability;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private Set<Appointment> appointments;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private Set<MedicalRecord> medicalRecords;

    // Getters and Setters...
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }
}