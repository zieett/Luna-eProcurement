package com.example.accountservice.controllers;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.dto.DepartmentDTO;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.entity.Department;
import com.example.accountservice.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    @PostMapping(value = "/department/set-department")
    public ResponseEntity<ResponseDTO<Department>> setDepartment(@RequestHeader("userInfo") String userInfo,@Valid @RequestBody DepartmentDTO departmentDTO) {
        return departmentService.setAccountDepartment(userInfo,departmentDTO);
    }
    @PostMapping(value = "/department/join-department")
    public ResponseEntity<ResponseDTO<Department>> joinDepartment(@RequestHeader("userInfo") String userInfo,@Valid @RequestBody DepartmentDTO departmentDTO) {
        return departmentService.joinDepartment(userInfo,departmentDTO);
    }
    @PostMapping(value = "/department")
    public ResponseEntity<ResponseDTO<Department>> createDepartment(@RequestHeader("userInfo") String userInfo,@Valid @RequestBody DepartmentDTO departmentDTO) {
        return departmentService.createDepartment(userInfo,departmentDTO);
    }
    @GetMapping(value = "/department")
    public ResponseEntity<ResponseDTO<Department>> getAllDepartmentInLegalEntity(@RequestHeader("userInfo") String userInfo) {
        return departmentService.getAllDepartmentInLegalEntity(userInfo);
    }
}
