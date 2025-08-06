package com.health.service;

import com.health.model.Doctor;
import com.health.repository.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepo doctorRepo;

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public Doctor updateAvailability(Long doctorId, String availability) {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setAvailability(availability);
        return doctorRepo.save(doctor);
    }
}