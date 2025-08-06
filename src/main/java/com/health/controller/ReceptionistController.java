package com.health.controller;

import com.health.dto.RegisterDoctorDto;
import com.health.model.Appointment;
import com.health.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/receptionist")
@CrossOrigin(origins = "http://localhost:3000")
public class ReceptionistController {

    @Autowired
    private AppointmentService appointmentService;

    // View All Appointments: GET /api/receptionist/appointments
    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    // Schedule Appointment: POST /api/receptionist/appointment?patientId=1&doctorId=1
    @PostMapping("/appointment")
    public ResponseEntity<Appointment> scheduleAppointment(@RequestParam Long patientId, @RequestParam Long doctorId, @RequestBody RegisterDoctorDto dto) {
        Appointment newAppointment = appointmentService.scheduleAppointment(patientId, doctorId, dto.getTime());
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }
    
    // Reschedule Appointment: PUT /api/receptionist/appointment-reschedule/{appointmentId}
    @PutMapping("/appointment-reschedule/{appointmentId}")
    public ResponseEntity<Appointment> rescheduleAppointment(@PathVariable Long appointmentId, @RequestBody RegisterDoctorDto dto) {
        Appointment updatedAppointment = appointmentService.rescheduleAppointment(appointmentId, dto.getTime());
        return ResponseEntity.ok(updatedAppointment);
    }
}