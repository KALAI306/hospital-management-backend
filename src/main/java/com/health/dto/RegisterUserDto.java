package com.health.dto;

public class RegisterUserDto {
    private String username;
    private String password;
    private String email;
    private String role;
    private String specialty;
    private String availability;

    // Getters and Setters...
    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }
    public String getPassword() { return password; }
    public void setPassword(String p) { this.password = p; }
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public String getRole() { return role; }
    public void setRole(String r) { this.role = r; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String s) { this.specialty = s; }
    public String getAvailability() { return availability; }
    public void setAvailability(String a) { this.availability = a; }
}