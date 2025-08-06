package com.health.controller;

import com.health.dto.AppointmentRequestDto;
import com.health.model.Appointment;
import com.health.model.Doctor;
import com.health.service.AppointmentService;
import com.health.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "http://localhost:3000")
public class PatientController {
    @Autowired private DoctorService doctorService;
    @Autowired private AppointmentService appointmentService;

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAvailableDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @PostMapping("/appointment")
    public ResponseEntity<Appointment> scheduleAppointment(@RequestParam Long patientId, @RequestParam Long doctorId, @RequestBody AppointmentRequestDto dto) {
        Appointment newAppointment = appointmentService.scheduleAppointment(patientId, doctorId, dto.getTime());
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> viewAppointments(@RequestParam Long patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsForPatient(patientId));
    }
}