package com.example.accountservice.repository;

import com.example.accountservice.entity.LegalEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntity,Long> {
    Optional<LegalEntity> findByCode(String code);
}

