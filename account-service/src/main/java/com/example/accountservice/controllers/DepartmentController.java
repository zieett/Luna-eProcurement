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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class DepartmentController {
    private final DepartmentService departmentService;
    @PostMapping(value = "/department/set-department", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<Department>> setDepartment(@RequestHeader("userInfo") String userInfo,@Valid @RequestBody DepartmentDTO departmentDTO) {
        return departmentService.setAccountDepartment(userInfo,departmentDTO);
    }
    @PostMapping(value = "/department/join-department", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<Department>> joinDepartment(@RequestHeader("userInfo") String userInfo,@Valid @RequestBody DepartmentDTO departmentDTO) {
        return departmentService.joinDepartment(userInfo,departmentDTO);
    }
}
