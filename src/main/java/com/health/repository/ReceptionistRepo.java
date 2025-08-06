package com.health.repository;

import com.health.model.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptionistRepo extends JpaRepository<Receptionist, Long> {
    // That's it! The body is empty.
}