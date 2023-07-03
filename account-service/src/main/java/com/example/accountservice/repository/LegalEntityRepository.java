package com.example.accountservice.repository;

import com.example.accountservice.entity.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntity,Long> {
    Optional<LegalEntity> findByCode(String code);

    LegalEntity findFirstByCode(String code);
}

