package com.example.accountservice.service.impl;

import com.example.accountservice.dto.LegalEntityDTO;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.entity.LegalEntity;
import com.example.accountservice.exception.LegalEntityNotFoundExeption;
import com.example.accountservice.repository.LegalEntityRepository;
import com.example.accountservice.service.LegalEntityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Slf4j
@Service
public class LegalEntityServiceImpl implements LegalEntityService {
    private final LegalEntityRepository legalEntityRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<LegalEntity>> getLegalEntitiesAccount(Long accountId) {
        return ResponseEntity.ok(legalEntityRepository.findAll());
    }

    @Override
    public ResponseEntity<ResponseDTO> createLegalEntity(LegalEntityDTO legalEntityDTO) {
        LegalEntity legalEntity = modelMapper.map(legalEntityDTO,LegalEntity.class);
        legalEntityRepository.save(legalEntity);
        return ResponseEntity.ok(new ResponseDTO("Sucessfully create legal entity", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ResponseDTO<LegalEntity>> getAllLegalEntity() {
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        if(CollectionUtils.isEmpty(legalEntityList)){
            throw new LegalEntityNotFoundExeption("Cannot find any legal entity");
        }
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(),legalEntityList));
    }
}
