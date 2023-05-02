package com.rmit.authservice.config;

import com.rmit.authservice.entity.UserCredential;
import com.rmit.authservice.repository.UserCredentialRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> userCredential = userCredentialRepository.findByEmail(username);
        return userCredential.map(CustomUserDetail::new).orElseThrow(() -> new UsernameNotFoundException("Email not found with email: "+ username ));
    }
}
