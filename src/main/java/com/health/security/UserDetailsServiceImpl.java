package com.health.security;

import com.health.model.User;
import com.health.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    // This method will always receive an EMAIL from now on.
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // We find the user by their EMAIL address.
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // === THIS IS THE CRITICAL LINE ===
        // We create the security principal using the EMAIL.
        // This ensures the token is created with the email, which fixes all other pages.
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), // Use EMAIL here, not getUsername()
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}