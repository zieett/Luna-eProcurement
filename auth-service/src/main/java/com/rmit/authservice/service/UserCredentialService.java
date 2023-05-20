package com.rmit.authservice.service;

import com.rmit.authservice.entity.UserCredential;
import com.rmit.authservice.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCredentialService {
    private final UserCredentialRepository userCredentialRepository;
    public UserCredential getUserById(Long id){
        return userCredentialRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Cannot find user"));
    }
}
