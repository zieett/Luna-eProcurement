package com.example.accountservice.service;

import com.example.accountservice.dto.DepartmentDTO;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.entity.Department;
import org.springframework.http.ResponseEntity;

public interface DepartmentService {
    ResponseEntity<ResponseDTO<Department>> joinDepartment(String userInfo,DepartmentDTO departmentDTO);
    ResponseEntity<ResponseDTO<Department>> setAccountDepartment(String userInfo, DepartmentDTO departmentDTO);
    ResponseEntity<ResponseDTO<Department>> createDepartment(String userInfo, DepartmentDTO departmentDTO);
    ResponseEntity<ResponseDTO<Department>> getAllDepartmentInLegalEntity(String userInfo);
}
