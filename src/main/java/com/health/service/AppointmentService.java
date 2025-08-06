package com.health.service;

import com.health.model.Appointment;
import com.health.model.Doctor;
import com.health.model.Patient;
import com.health.repository.AppointmentRepo;
import com.health.repository.DoctorRepo;
import com.health.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // CORRECT import
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }
    
    public List<Appointment> getAppointmentsForPatient(Long patientId) {
        return appointmentRepo.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsForDoctor(Long doctorId) {
        return appointmentRepo.findByDoctorId(doctorId);
    }

    // CORRECTED: Parameter is now LocalDateTime
    public Appointment scheduleAppointment(Long patientId, Long doctorId, LocalDateTime appointmentTime) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(appointmentTime); // This now correctly stores the time
        appointment.setStatus("SCHEDULED");

        return appointmentRepo.save(appointment);
    }
    
    // CORRECTED: Parameter is now LocalDateTime
    public Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newTime) {
        Appointment appointment = appointmentRepo.findById(appointmentId)
                 .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));
        
        appointment.setAppointmentTime(newTime);
        appointment.setStatus("RESCHEDULED");
        
        return appointmentRepo.save(appointment);
    }
}