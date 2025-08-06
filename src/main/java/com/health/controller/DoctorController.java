package com.health.controller;

import com.health.model.Appointment;
import com.health.model.Doctor;
import com.health.service.AppointmentService;
import com.health.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin(origins = "http://localhost:3000")
public class DoctorController {

    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private DoctorService doctorService;

    // View Appointments: GET /api/doctor/appointments?doctorId=1
    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAppointments(@RequestParam Long doctorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsForDoctor(doctorId));
    }
    
    // Manage Availability: POST /api/doctor/availability?doctorId=1&availability=Monday 9-5
    @PostMapping("/availability")
    public ResponseEntity<Doctor> manageAvailability(@RequestParam Long doctorId, @RequestParam String availability) {
        return ResponseEntity.ok(doctorService.updateAvailability(doctorId, availability));
    }
}