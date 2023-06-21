package com.example.accountservice.service;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.dto.LegalEntityDTO;
import com.example.accountservice.dto.LegalEntityInfoDTO;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.entity.LegalEntity;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface LegalEntityService {

    ResponseEntity<List<LegalEntity>> getLegalEntitiesAccount(Long accountId);

    ResponseEntity<ResponseDTO> createLegalEntity(String userInfo, LegalEntityDTO legalEntityDTO);

    ResponseEntity<ResponseDTO<LegalEntity>> getAllLegalEntity();

    ResponseEntity<ResponseDTO<AccountDTO>> getAllAccountInEntity(String entityCode);

    ResponseEntity<ResponseDTO<LegalEntityInfoDTO>> findLegalEntityInfo(String entityCode);
}
