package com.health.service;

// Import the DTOs we will be using
import com.health.dto.LoginRequestDto;
import com.health.dto.LoginResponseDto;
import com.health.dto.RegisterUserDto;

// Import the models
import com.health.model.Doctor;
import com.health.model.Patient;
import com.health.model.Receptionist;
import com.health.model.User;

// Import the repositories
import com.health.repository.DoctorRepo;
import com.health.repository.PatientRepo;
import com.health.repository.ReceptionistRepo;
import com.health.repository.UserRepo;

// Import security components
import com.health.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    // Autowired dependencies
    @Autowired private PatientRepo patientRepo;
    @Autowired private DoctorRepo doctorRepo;
    @Autowired private ReceptionistRepo receptionistRepo;
    @Autowired private UserRepo userRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private JwtUtil jwtUtil;

    // The registerNewUser method does not need to change.
    @Transactional
    public User registerNewUser(RegisterUserDto dto) {
        if (userRepo.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username '" + dto.getUsername() + "' is already taken.");
        }

        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email '" + dto.getEmail() + "' is already in use.");
        }

        String role = dto.getRole().toUpperCase();
        String hashedPassword = passwordEncoder.encode(dto.getPassword());

        switch (role) {
            case "PATIENT":
                Patient patient = new Patient();
                patient.setUsername(dto.getUsername());
                patient.setEmail(dto.getEmail());
                patient.setPassword(hashedPassword);
                patient.setRole(role);
                return patientRepo.save(patient);

            case "DOCTOR":
                Doctor doctor = new Doctor();
                doctor.setUsername(dto.getUsername());
                doctor.setEmail(dto.getEmail());
                doctor.setPassword(hashedPassword);
                doctor.setRole(role);
                doctor.setSpecialty(dto.getSpecialty());
                doctor.setAvailability(dto.getAvailability());
                return doctorRepo.save(doctor);
            
            case "RECEPTIONIST":
                Receptionist receptionist = new Receptionist();
                receptionist.setUsername(dto.getUsername());
                receptionist.setEmail(dto.getEmail());
                receptionist.setPassword(hashedPassword);
                receptionist.setRole(role);
                return receptionistRepo.save(receptionist);

            default:
                throw new IllegalArgumentException("Invalid role specified: " + dto.getRole());
        }
    }

    // This is the fully corrected method for email-based login
    public LoginResponseDto loginUser(LoginRequestDto loginRequest) {
        // 1. Authenticate using the email as the principal
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        // 2. Load user details using the email
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        
        // 3. Fetch the full User entity using the email to get the ID and other details
        final User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found after successful authentication."));

        // 4. Generate the token (this still uses the username from UserDetails, which is correct)
        final String token = jwtUtil.generateToken(userDetails);

        // 5. Return the response with the token and full user details
        return new LoginResponseDto(token, user.getId(), user.getUsername(), user.getRole());
    }
}
