package com.example.accountservice.service;

import com.example.accountservice.dto.LegalEntityDTO;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.entity.LegalEntity;
import com.example.accountservice.repository.LegalEntityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface LegalEntityService {

    ResponseEntity<List<LegalEntity>> getLegalEntitiesAccount(Long accountId);

    ResponseEntity<ResponseDTO> createLegalEntity(LegalEntityDTO legalEntityDTO);

    ResponseEntity<ResponseDTO<LegalEntity>> getAllLegalEntity();
}
