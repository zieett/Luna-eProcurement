package com.example.accountservice.repository;

import com.example.accountservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,String> {
    List<Department> getDepartmentByLegalEntityCode(String legalEntityCode);

    List<Department> findByLegalEntityCode(String legalEntityCode);
}
