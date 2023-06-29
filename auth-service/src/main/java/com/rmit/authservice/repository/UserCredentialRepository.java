package com.rmit.authservice.repository;

import com.rmit.authservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential,Long> {

    Optional<UserCredential> findByEmail(String username);

    void deleteByEmail(String email);
}
